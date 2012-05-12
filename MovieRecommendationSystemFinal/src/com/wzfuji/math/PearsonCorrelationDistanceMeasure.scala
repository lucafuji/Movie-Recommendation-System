package com.wzfuji.math

import org.apache.mahout.math.Vector

/**
 * @author lucafuji
 * Pearson Correlation Distance Measure between two vectors, substract both vector
 * by their respective mean
 */
class PearsonCorrelationDistanceMeasure extends DistanceMeasure{
	override def computeDistance(v1:Vector,v2:Vector):Double = {
	  val leftVec = normalizeVector(v1)
	  val rightVec = normalizeVector(v2)
	  val numeratorVec = for(tuple <- leftVec zip rightVec) yield {
	    tuple._1*tuple._2
	  }
	  val numerator = numeratorVec.reduceLeft((_:Double)+(_:Double))
	  val leftDenomVec = for(elem<- leftVec) yield{
	    elem*elem
	  }
	 val leftDenom = Math.pow(leftDenomVec.reduceLeft((_:Double)+(_:Double)),0.5)
	 val rightDenomVec = for(elem<- rightVec) yield{
	     elem*elem
	  }
	 val rightDenom = Math.pow(rightDenomVec.reduceLeft((_:Double)+(_:Double)),0.5)
	 return numerator/(leftDenom*rightDenom)
	}
	
	private def normalizeVector(v1:Vector):IndexedSeq[Double] = {
	  val leftValues = for(i <- 0 to v1.size()) yield {v1.get(i)}
	  val leftMean = leftValues.reduceLeft((_:Double)+(_:Double))/v1.size()
	  for(i <- 0 to v1.size()) yield {v1.get(i) - leftMean}
	}
}