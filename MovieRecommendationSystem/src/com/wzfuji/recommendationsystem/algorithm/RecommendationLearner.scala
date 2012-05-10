package com.wzfuji.recommendationsystem.algorithm.CB

import scala.collection.immutable.List
import com.wzfuji.recommendationsystem.instance.RSInstance

trait RecommendationLearner {
	def learn(instances:List[RSInstance]):Predictor
}