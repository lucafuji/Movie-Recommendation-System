package com.wzfuji.recommendationsystem.algorithm.CB

import com.wzfuji.recommendationsystem.algorithm.RecommendationLearner
import com.wzfuji.recommendationsystem.instance.RSInstance
import com.wzfuji.recommendationsystem.algorithm.Predictor
class MaxentropyLearner extends RecommendationLearner {
	override def learn(instances:List[RSInstance]):Predictor={
	  return new MaxentropyPredictor()
	}
}