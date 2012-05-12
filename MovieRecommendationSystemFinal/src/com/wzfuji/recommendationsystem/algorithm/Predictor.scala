package com.wzfuji.recommendationsystem.algorithm
import com.wzfuji.recommendationsystem.instance.RSInstance
/**
 * @author lucafuji
 * a predictor which is the result of learner
 */
abstract class Predictor {
	/**
	 * @param userId
	 * @param itemId
	 * @return the rating of the user for the item
	 */
	def predict(userId:Int,itemId:Int):PredictionResult
}