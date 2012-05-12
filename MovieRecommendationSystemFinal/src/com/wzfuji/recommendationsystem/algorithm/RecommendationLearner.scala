package com.wzfuji.recommendationsystem.algorithm

import com.wzfuji.recommendationsystem.instance.RSInstance
/**
 * @author lucafuji
 * generic recommendation learner
 */
abstract class RecommendationLearner {
	/**
	 * @param instances a list of (user,item,rating) tuple
	 * @return predictor 
	 */
	def learn(instances:Iterator[RSInstance]):Predictor
}