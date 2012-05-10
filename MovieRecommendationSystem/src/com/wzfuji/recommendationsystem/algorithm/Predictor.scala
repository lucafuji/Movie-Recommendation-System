package com.wzfuji.recommendationsystem.algorithm
import com.wzfuji.recommendationsystem.instance.RSInstance
abstract class Predictor {
	def predict(userId:Int,itemId:Int):PredictionResult
}