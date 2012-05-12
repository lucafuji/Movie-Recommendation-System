package com.wzfuji.recommendationsystem.algorithm.CF
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
import com.wzfuji.db.DAO.OccupationHome
import com.wzfuji.db.entity.Occupation
class TestClusterLearner extends TestCase {
  def testClusterLearner() {
    val cLearner: RecommendationLearner = new ClusterLearner();
    val expr = new Experiment(cLearner, new MAEMetric, "result/cluster")
    expr.crossValidation()
  }
}