package com.wzfuji.recommendationsystem.experiment

import com.wzfuji.recommendationsystem.experiment.metric.Metric
import com.wzfuji.recommendationsystem.algorithm.RecommendationLearner
import com.wzfuji.recommendationsystem.instance.RSInstance
import com.wzfuji.recommendationsystem.experiment.metric.RatingPair
import org.apache.commons.math.stat.descriptive.moment.Mean
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation
import java.io.File
import com.wzfuji.util.FileUtils

/**
 * @author lucafuji
 * learner: the recommendation learner we want to evaluate
 * metric: evaluation metric
 * filaName: the file where we store the evaluation result
 */
class Experiment(learner:RecommendationLearner,metric:Metric,fileName:String) {
	/**
	 * do test for the learner
	 * @param traningInstances
	 * @param testInstances
	 * @return metric value
	 */
	def doExperiment(traningInstances:Iterator[RSInstance],testInstances:Iterator[RSInstance]):Double={
	  val predictor = learner.learn(traningInstances)
	  val results = for(testInstances<-testInstances) yield{
	     new RatingPair(predictor.predict(testInstances.userId,testInstances.itemId).predictRating,
	         testInstances.rating)
	  }
	  metric.evaluate(results)
	}
	
	/**
	 * do 7 fold cross validation
	 */
	def crossValidation(){
	  val prefixs = "12345ab"
	  val maes =new Array[Double](prefixs.length())
	  var i=0
	  val meantool = new Mean
	  val sdtool = new StandardDeviation
	  for(c <-prefixs) {
	    val trainingInstances = RSIntanceReader.readRSInstance("testdata/"+"u"+c+".base")
	    val testInstances = RSIntanceReader.readRSInstance("testdata/"+"u"+c+".test")
	    maes(i) = doExperiment(trainingInstances,testInstances)
	    println("MAE"+i+" = "+maes(i))
	    i+=1
	  }
	  val averageMAE = meantool.evaluate(maes)
	  val sd = sdtool.evaluate(maes)
	  println("Algorithms:Content-Based Maxentropy")
	  println("Average MAE:"+averageMAE)
	  println("Standard Deviation:"+sd)
	  val outputFile = new File(fileName)
	  FileUtils.appendToFile(outputFile, "Algorithms:Content-Based Maxentropy")
	  FileUtils.appendToFile(outputFile, "Average MAE:"+averageMAE)
	  FileUtils.appendToFile(outputFile, "Standard Deviation:"+sd)
	}
}