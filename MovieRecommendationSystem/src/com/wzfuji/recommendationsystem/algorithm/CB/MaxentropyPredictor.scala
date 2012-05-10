package com.wzfuji.recommendationsystem.algorithm.CB

import com.wzfuji.recommendationsystem.algorithm.Predictor
import com.wzfuji.recommendationsystem.algorithm.PredictionResult
import com.wzfuji.recommendationsystem.instance.RSInstance

class MaxentropyPredictor extends Predictor {
override def predict(instance:RSInstance):PredictionResult ={
	  println("Max entropy predicot")
	  return new PredictionResult(1,1,2.0)
	}
}