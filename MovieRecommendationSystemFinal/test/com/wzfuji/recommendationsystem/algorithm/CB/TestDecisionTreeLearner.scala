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
class TestDecisionTreeLearner extends TestCase {
  def testDecisionTreeLeaner() {
    val treeLearner: RecommendationLearner = new DecisionTreeLearner();
    val expr = new Experiment(treeLearner, new MAEMetric, "result/tree")
    expr.crossValidation()
  }
}