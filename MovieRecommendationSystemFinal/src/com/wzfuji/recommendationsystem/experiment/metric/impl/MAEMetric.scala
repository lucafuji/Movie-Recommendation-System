package com.wzfuji.recommendationsystem.experiment.metric.impl

import com.wzfuji.recommendationsystem.experiment.metric.Metric
import scala.collection.immutable.List
import com.wzfuji.recommendationsystem.experiment.metric.RatingPair
import scala.Math
/**
 * @author lucafuji
 * this metric is computed by the sum up of all the absolute value of the difference between the predicted rating
 * and true rating,and compute the average
 */
class MAEMetric extends Metric {
  def evaluate(results: Iterator[RatingPair]): Double = {
    val values = for(pair<-results) yield{
    	Math.abs(pair.predictedRating-pair.trueRating)
    }
    val mae = values.reduceLeft((_:Double)+(_:Double))
    mae/results.size
  }
}