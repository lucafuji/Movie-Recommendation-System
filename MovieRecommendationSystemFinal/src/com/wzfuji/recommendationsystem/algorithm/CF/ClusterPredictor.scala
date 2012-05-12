package com.wzfuji.recommendationsystem.algorithm.CF

import com.wzfuji.recommendationsystem.algorithm.Predictor
import com.wzfuji.recommendationsystem.algorithm.PredictionResult
import com.wzfuji.recommendationsystem.instance.RSInstance
import com.wzfuji.db.DAO._
import com.wzfuji.db.entity._
import com.wzfuji.recommendationsystem.exception._
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import weka.clusterers.EM
import com.wzfuji.util.ReflectionUtils
import com.wzfuji.recommendationsystem.util.ARRFWriter
import weka.core.converters.ConverterUtils.DataSource
import scala.collection.JavaConversions._
import com.wzfuji.math.AdjustCosineDistance
import com.wzfuji.recommendationsystem.algorithm.CF
import IteminfoVectorConverter._

/**
 * @author lucafuji
 * predict the rating by find the items in the same cluster of the item that we
 * want to predict and compute the weighted rating of those item according their similarity
 *
 * use Adjusted Cosine Distance as the distance measure
 */
class ClusterPredictor(neighbour_limit: Int) extends Predictor {
  private val filePrefix = "tmp/cluster/learner"
  private val relationName = "MovieRecommendationSystem"
  private val attrMap = ReflectionUtils.getAttrTypeMap(new Iteminfo().getClass())
  def predict(userId: Int, itemId: Int): PredictionResult = {
    val clusterId = getCluster(userId, itemId)
    val neighbours = getNeighbour(userId, clusterId)
    new PredictionResult(userId, itemId, weightedRating(userId, itemId, neighbours))
  }

  /**
   * compute the weighted value according to the rating of the neighbours and similarity between them
   * @param currentuserId the user we want to predict
   * @param itemId the item we want to predict
   * @param neighbours neighbours of the item that we want predict
   * @return predicted rating
   */
  private def weightedRating(currentuserId: Int, itemId: Int, neighbours: List[Iteminfo]): Double = {
    var weightedRatingSum = 0.0
    var weightSum = 0.0
    val ratingHome = new RatingHome()
    for (neighbour <- neighbours) {
      val commonUserIds = getCommonUser(itemId, neighbour.getMovieid())
      val vectorTuples = for (userId <- commonUserIds) yield {
        new AverageUserRatingHome().findById(userId).getAveragerating()
      }
      val srcVector = for (userId <- commonUserIds) yield {
        ratingHome.findById(new RatingId(userId, itemId))
      }
      val dstVector = for (userId <- commonUserIds) yield {
        ratingHome.findById(new RatingId(userId, neighbour.getMovieid()))
      }
      val distance = new AdjustCosineDistance(
        toVector(vectorTuples.asInstanceOf[List[Double]])).computeDistance(toVector(srcVector.asInstanceOf[List[Double]]),
        toVector(dstVector.asInstanceOf[List[Double]]))
      if (distance != 0) {
        weightedRatingSum += 1 / distance * ratingHome.findById(new RatingId(currentuserId, neighbour.getMovieid())).getRating()
        weightSum += 1 / distance
      }
    }
    weightedRatingSum / weightSum
  }

  /**
   * search for the item that in the same cluster with the item we want to predict
   * @param userId specify which profile we want to use
   * @param clusterId the clusterid of item we want to predict
   * @return neigbours
   */
  private def getNeighbour(userId: Int, clusterId: Int) = {
    val query = "select item from Iteminfo item,ItemCluster cluster" +
      " where cluster.id.userId = " + userId + " and cluster.clusterId = " +
      clusterId + " item.movieid = cluster.id.itemId"
    new IteminfoHome().findEntityBySql(query, neighbour_limit).asInstanceOf[List[Iteminfo]]
  }

  /**
   * find the collection of user that has rated two items
 * @param itemId1
 * @param itemId2
 * @return
 */
  private def getCommonUser(itemId1: Int, itemId2: Int): List[Int] = {
    val query = "select x.id.userId from rating x,rating y " +
      "where x.id.itemId = " + itemId1 + " and y.id.itemId =" + itemId2 + " and x.id.userId = y.id.userId"
    new UserinfoHome().findEntityBySql(query).toList.asInstanceOf[List[Int]]
  }
  
  /**
   * 
 * @param userId
 * @param itemId
 * @return the clusterid of the item in the profile of the user
 */
  private def getCluster(userId: Int, itemId: Int): Int = {
    val clHome = new ClusterUserProfileHome()
    val model = clHome.findById(userId)
    if (model == null) {
      throw new NoSuchClusterProfileException
    }
    val inputStream = new ByteArrayInputStream(model.getSerializedObject())
    val clusterModel = new ObjectInputStream(inputStream).readObject().asInstanceOf[EM]
    val writer = new ARRFWriter(filePrefix + userId, relationName, attrMap, null)
    val itemInfo = new IteminfoHome().findById(itemId)
    if (itemInfo != null) {
      writer.appendInstance(itemInfo, null)
    } else {
      throw new NoSuchItemException
    }
    val source = new DataSource(filePrefix + userId);
    val instances = source.getDataSet();
    writer.close()
    return clusterModel.clusterInstance(instances.get(0))
  }

}