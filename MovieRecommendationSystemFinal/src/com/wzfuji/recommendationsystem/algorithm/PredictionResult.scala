package com.wzfuji.recommendationsystem.algorithm

/**
 * @author lucafuji
 * result of the prediction with the predicted value for the user and item
 */
class PredictionResult(val userId:Int,val itemId:Int,val predictRating:Double)