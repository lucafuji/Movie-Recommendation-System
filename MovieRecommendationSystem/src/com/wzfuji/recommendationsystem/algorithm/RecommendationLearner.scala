package com.wzfuji.recommendationsystem.algorithm

import com.wzfuji.recommendationsystem.instance.RSInstance
abstract class RecommendationLearner {
	def learn(instances:List[RSInstance]):Predictor
}