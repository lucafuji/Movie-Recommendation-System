package com.wzfuji.recommendationsystem.experiment.metric

/**
 * @author lucafuji
 * measuring the performance of a specified learner
 */
trait Metric {
	def evaluate(results:Iterator[RatingPair]):Double
}

class RatingPair(val predictedRating:Double,val trueRating:Double);