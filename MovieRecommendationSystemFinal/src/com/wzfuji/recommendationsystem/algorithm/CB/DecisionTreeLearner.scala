package com.wzfuji.recommendationsystem.algorithm.CB

import com.wzfuji.recommendationsystem.algorithm.RecommendationLearner
import com.wzfuji.recommendationsystem.algorithm.Predictor
import com.wzfuji.recommendationsystem.instance.RSInstance
import java.io.File
import com.wzfuji.recommendationsystem.instance.RSInstance
import com.wzfuji.recommendationsystem.algorithm.Predictor
import com.wzfuji.db.DAO._
import com.wzfuji.db.entity._
import scala.io._
import com.wzfuji.recommendationsystem.util.ARRFWriter
import com.wzfuji.util.ReflectionUtils
import scala.collection.mutable.HashMap
import weka.core.converters.ConverterUtils.DataSource
import weka.clusterers.EM
import weka.clusterers.AbstractDensityBasedClusterer
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.ObjectOutputStream
import com.wzfuji.db.util.Operation
import weka.core.Instances
import weka.classifiers.trees.M5P
import weka.classifiers.trees.LMT
import weka.classifiers.Classifier

/**
 * @author lucafuji
 * content-based recommendation learner ,implemented by LMT decision tree
 * with a logistic model
 */
class DecisionTreeLearner extends RecommendationLearner {
  /**
 *  tmp file location
 */
private val filePrefix = "tmp/tree/learner"
  private val relationName = "MovieRecommendationSystem"

  
  /* (non-Javadoc)
 * @see com.wzfuji.recommendationsystem.algorithm.RecommendationLearner#learn(scala.collection.Iterator)
 */
override def learn(instances: Iterator[RSInstance]): Predictor = {
    userFileModelMap.clear()
    userIds = List[Int]()
    // first extract instance from instances and group them by userId
    instances.foreach(writeInstanceToFile)
    userIds.foreach(generateModel)
    return new DecisionTreePredictor()
  }

  private def getWriter(userId: Int): ARRFWriter = {
    if (!userFileModelMap.contains(userId)) {
      val writer = new ARRFWriter(filePrefix + userId, relationName, attrMap, ("rating","Double"))
      userFileModelMap.put(userId, writer)
    }
    return userFileModelMap.get(userId).get
  }

  /** generate the profile model for each user
 * @param userId
 */
private def generateModel(userId: Int) {
    val writer = this.getWriter(userId)
    val source = new DataSource(filePrefix+userId);
    val instances = source.getDataSet();
    val treeModel = new LMT()
    treeModel.buildClassifier(instances)
    this.writeModelToDB(userId, treeModel)
    writer.close()
  }
  
 
  private def writeModelToDB(userId: Int, model: Classifier) {
    val treeProfileDao = new DecisionTreeUserProfileHome()
    val existingModel = treeProfileDao.findById(userId)
    val byteStream = new ByteArrayOutputStream()
    val outputStream = new ObjectOutputStream(byteStream)
    outputStream.writeObject(model)
    try {
      val op: Operation = new Operation()
      op.setDao(treeProfileDao)
      val parameters = Array[Object](new DecisionTreeUserProfile(userId, byteStream.toByteArray()))
      op.setParameter(parameters)
      if (existingModel == null) {
        op.setOperationName("persist")
      } else {
        op.setOperationName("merge")
      }
      op.execute()
    } finally {
      byteStream.close()
      outputStream.close()
    }
  }

  private def writeInstanceToFile(instance: RSInstance) {

    val userId = instance.userId
    userIds ::= userId
    val itemId = instance.itemId
    val rating = instance.rating
    val writer = this.getWriter(userId)
    val itemInfo = new IteminfoHome().findById(itemId)
    if (itemInfo != null) {
      writer.appendInstance(itemInfo,rating)
    }
  }

  private val userFileModelMap = new HashMap[Int, ARRFWriter]()
  private var userIds = List[Int]()
  private val attrMap = ReflectionUtils.getAttrTypeMap(new Iteminfo().getClass())
}