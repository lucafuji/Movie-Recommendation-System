package com.wzfuji.recommendationsystem.algorithm.CB
import junit.framework.TestCase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import com.wzfuji.recommendationsystem.algorithm.RecommendationLearner
import com.wzfuji.recommendationsystem.algorithm.PredictionResult
import com.wzfuji.recommendationsystem.algorithm.Predictor
import com.wzfuji.recommendationsystem.instance.RSInstance
import com.wzfuji.recommendationsystem.experiment._
import com.wzfuji.recommendationsystem.experiment.metric.impl._
import com.wzfuji.db.DAO.DAOBase
class TestMaxEntropyLearner extends TestCase {
  def testMaxentropyLeaner() {
    val meLearner: RecommendationLearner = new MaxentropyLearner();
    val expr = new Experiment(meLearner, new MAEMetric, "result/maxentropy")
    expr.crossValidation()
  }
}