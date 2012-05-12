package com.wzfuji.recommendationsystem.algorithm.CB

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
import weka.classifiers.trees.LMT


/**
 * @author lucafuji
 * predict the rating from the decision tree classifier
 */
class DecisionTreePredictor extends Predictor{
  private val filePrefix = "tmp/tree/learner"
  private val relationName = "MovieRecommendationSystem"
  private val attrMap = ReflectionUtils.getAttrTypeMap(new Iteminfo().getClass())
  
  def predict(userId: Int, itemId: Int): PredictionResult = {
    val treeHome = new DecisionTreeUserProfileHome()
    val model = treeHome.findById(userId)
    if (model == null) {
      throw new NoSuchTreeProfileException
    }
    
    //deserialize  model from database
    val inputStream = new ByteArrayInputStream(model.getSerializedObject())
    val treeModel = new ObjectInputStream(inputStream).readObject().asInstanceOf[LMT]
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
    new PredictionResult(userId,itemId,treeModel.classifyInstance(instances.get(0)))
  } 
}