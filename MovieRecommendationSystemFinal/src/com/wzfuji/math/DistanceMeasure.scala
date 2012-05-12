package com.wzfuji.math

import org.apache.mahout.math.Vector
/**
 * @author lucafuji
 * interface for compute the distance between two vectors 
 * using to measure the similarity between two users or two items
 */
trait DistanceMeasure {
	/**
	 * @param v1 first vector
	 * @param v2 second vector
	 * @return their distance
	 */
	def computeDistance(v1:Vector,v2:Vector):Double
}