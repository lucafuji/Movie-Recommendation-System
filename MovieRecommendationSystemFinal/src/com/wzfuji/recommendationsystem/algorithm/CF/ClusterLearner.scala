package com.wzfuji.recommendationsystem.algorithm.CF
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

/**
 * @author lucafuji
 * implement the learner by build clusters for each user 
 * using EM clustering algorithm
 */
class ClusterLearner extends RecommendationLearner {
  private val filePrefix = "tmp/cluster/learner"
  private val relationName = "MovieRecommendationSystem"
  private val neighbour_limit = 10
  
  override def learn(instances: Iterator[RSInstance]): Predictor = {
    userFileModelMap.clear()
    userIds = List[Int]()
    // first extract instance from instances and group them by userId
    instances.foreach(writeInstanceToFile)
    userIds.foreach(generateModel)
    return new ClusterPredictor(neighbour_limit)
  }

  private def getWriter(userId: Int): ARRFWriter = {
    if (!userFileModelMap.contains(userId)) {
      val writer = new ARRFWriter(filePrefix + userId, relationName, attrMap, null)
      userFileModelMap.put(userId, writer)
    }
    return userFileModelMap.get(userId).get
  }

  private def generateModel(userId: Int) {
    val writer = this.getWriter(userId)
    val source = new DataSource(filePrefix+userId);
    val instances = source.getDataSet();
    val clusterModel = new EM()
    clusterModel.buildClusterer(instances)
    this.writeModelToDB(userId, clusterModel)
    this.storeClusterToDB(clusterModel,instances,userId)
    writer.close()
  }
  
  private def storeClusterToDB(model: AbstractDensityBasedClusterer,instances:Instances,userId:Int){
    val size = instances.size()
    val clusterDao = new ItemClusterHome()
    for(i<-0 to size){
      val clusterId =model.clusterInstance(instances.get(i))
      val itemId = instances.get(i).attribute(instances.get(i).numAttributes()-1).asInstanceOf[Int]
      val itemClusterId = new ItemClusterId(userId,itemId)
      val existingModel = clusterDao.findById(itemClusterId)
      val op: Operation = new Operation()
      op.setDao(clusterDao)
      val parameters = Array[Object](new ItemCluster(itemClusterId, clusterId))
      op.setParameter(parameters)
      if (existingModel == null) {
        op.setOperationName("persist")
      } else {
        op.setOperationName("merge")
      }
      op.execute()
    }
  }

  private def writeModelToDB(userId: Int, model: AbstractDensityBasedClusterer) {
    val clProfileDao = new ClusterUserProfileHome()
    val existingModel = clProfileDao.findById(userId)
    val byteStream = new ByteArrayOutputStream()
    val outputStream = new ObjectOutputStream(byteStream)
    outputStream.writeObject(model)
    try {
      val op: Operation = new Operation()
      op.setDao(clProfileDao)
      val parameters = Array[Object](new ClusterUserProfile(userId, byteStream.toByteArray()))
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
      writer.appendInstance(itemInfo, null)
    }
  }

  private val userFileModelMap = new HashMap[Int, ARRFWriter]()
  private var userIds = List[Int]()
  private val attrMap = ReflectionUtils.getAttrTypeMap(new Iteminfo().getClass())
}