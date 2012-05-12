package com.wzfuji.recommendationsystem.algorithm.CB

import com.wzfuji.recommendationsystem.algorithm.Predictor
import com.wzfuji.recommendationsystem.algorithm.PredictionResult
import com.wzfuji.recommendationsystem.instance.RSInstance
import opennlp.maxent.GISModel
import com.wzfuji.db.DAO._
import com.wzfuji.db.entity._
import com.wzfuji.recommendationsystem.exception.NoSuchMaxentProfileException
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import opennlp.maxent.io.BinaryGISModelReader
import com.wzfuji.recommendationsystem.exception.NoSuchItemException
import com.wzfuji.recommendationsystem.dataConverter.MaxEntropyInstance

class MaxentropyPredictor extends Predictor {
	override def predict(userId:Int,itemId:Int):PredictionResult ={
	  val model = getModel(userId) 
	  return new PredictionResult(userId,itemId,this.getRating(itemId,model))
	}
	
	private def getRating(itemId:Int,model:GISModel):Double={
	  val iteminfoDAO = new IteminfoHome()
	  val iteminfo = iteminfoDAO.findById(itemId)
	  if(iteminfo == null){
	    throw new NoSuchItemException
	  }else{
	    val maxentInstance = new MaxEntropyInstance[Iteminfo,Double](iteminfo,0.0)
	    val probs = model.eval(maxentInstance.toFeatureValuePairArray())
	    val numberOfOutcomes = model.getNumOutcomes()
	    val weigtedRatings = for(i<-0 to numberOfOutcomes) yield {
	      model.getOutcome(i).toDouble*probs(i)
	    }
	   weigtedRatings.reduceLeft((_:Double)+(_:Double))
	  }
	}
	
	private def getModel(userId:Int):GISModel={
	  val meProfileDao = new MaxentUserProfileHome()
	  val modelProfle = meProfileDao.findById(userId)
	  if(modelProfle == null){
	    throw new NoSuchMaxentProfileException
	  }
	  val byteInputStream = new ByteArrayInputStream(modelProfle.getSerializedObject())
	  val inputStream = new DataInputStream(byteInputStream)
	  val modelReader = new BinaryGISModelReader(inputStream)
	  try{
	    return modelReader.getModel().asInstanceOf[(GISModel)]
	  }finally{
	    byteInputStream.close()
	    inputStream.close()
	  }
	}
}