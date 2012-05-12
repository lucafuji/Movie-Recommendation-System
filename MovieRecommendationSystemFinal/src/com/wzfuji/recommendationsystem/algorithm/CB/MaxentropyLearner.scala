package com.wzfuji.recommendationsystem.algorithm.CB

import com.wzfuji.recommendationsystem.algorithm.RecommendationLearner
import scala.collection.mutable.HashMap
import com.wzfuji.recommendationsystem.instance.RSInstance
import com.wzfuji.recommendationsystem.algorithm.Predictor
import java.io.File
import com.wzfuji.db.DAO._
import com.wzfuji.db.entity._
import scala.io._
import com.wzfuji.recommendationsystem.dataConverter.MaxEntropyInstance
import com.wzfuji.util.FileUtils
import java.io.FileReader
import opennlp.model.EventStream
import opennlp.maxent.BasicEventStream
import opennlp.maxent.PlainTextByLineDataStream
import opennlp.maxent.GIS
import opennlp.maxent.GISModel
import java.io.DataOutputStream
import java.io.ByteArrayOutputStream
import opennlp.maxent.io.BinaryGISModelWriter
import com.wzfuji.db.util.Operation

/**
 * @author lucafuji
 * using max entropy to do content-based predictor
 */
class MaxentropyLearner extends RecommendationLearner {

  private val filePrefix = "tmp/maxent/learner"
  override def learn(instances: Iterator[RSInstance]): Predictor = {
    userFileModelMap.clear()
    userIds = List[Int]()
    // first extract instance from instances and group them by userId
    instances.foreach(writeInstanceToFile)
    userIds.foreach(generateModel)
    return new MaxentropyPredictor()
  }

  private def getFile(userId: Int): File = {
    if (!userFileModelMap.contains(userId)) {
      val file = new File(filePrefix + userId)
      userFileModelMap.put(userId, file)
    }
    return userFileModelMap.get(userId).get
  }

  private def generateModel(userId: Int) {
    val file = this.getFile(userId)
    val datafr = new FileReader(file);
    val es = new BasicEventStream(new PlainTextByLineDataStream(
      datafr));
    val model = GIS.trainModel(es, 400, 30, true, false);
    this.writeModelToDB(userId, model)
    datafr.close()
  }

  /**
   * write max entropy model to database
   * @param userId
   * @param model
   */
  private def writeModelToDB(userId: Int, model: GISModel) {
    val meProfileDao = new MaxentUserProfileHome()
    val existingModel = meProfileDao.findById(userId)
    val byteStream = new ByteArrayOutputStream()
    val outputStream = new DataOutputStream(byteStream)
    val modelWriter = new BinaryGISModelWriter(model, outputStream)
    try {
      modelWriter.persist()
      val op: Operation = new Operation()
      op.setDao(meProfileDao)
      val parameters = Array[Object](new MaxentUserProfile(userId, byteStream.toByteArray()))
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
      modelWriter.close()
    }
  }

  private def writeInstanceToFile(instance: RSInstance) {

    val userId = instance.userId
    userIds ::= userId
    val itemId = instance.itemId
    val rating = instance.rating
    val file = this.getFile(userId)
    val itemInfo = new IteminfoHome().findById(itemId)
    if (itemInfo != null) {
      val maxentEntity = new MaxEntropyInstance[Iteminfo, Double](itemInfo, rating)
      FileUtils.appendToFile(file, maxentEntity.toValuePairStringWithTag())
    }
  }

  private val userFileModelMap = new HashMap[Int, File]()
  private var userIds = List[Int]()
}