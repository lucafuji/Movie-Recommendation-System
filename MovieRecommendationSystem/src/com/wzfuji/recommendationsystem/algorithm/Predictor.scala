package com.wzfuji.recommendationsystem.algorithm.CB

import com.wzfuji.recommendationsystem.instance.RSInstance;
trait Predictor {
	def predict(instance:RSIntance):PredictionResult
}