package com.wzfuji.math
import org.apache.mahout.math.Vector
import scala.Math

/**
 * @author lucafuji
 * cosine distance measure of two vectors
 */
class CosineDistanceMeasure extends DistanceMeasure{
	def computeDistance(v1:Vector,v2:Vector):Double = {
	  val vectorSize = v1.size()
	  val numeratorVec = for(i<- 0 to vectorSize) yield{
	    v1.get(i)*v2.get(i)
	  }
	 val numerator = numeratorVec.reduceLeft((_:Double)+(_:Double))
	 val leftVec = for(i<- 0 to vectorSize) yield{
	    v1.get(i)*v1.get(i)
	  }
	 val leftDenom = Math.pow(leftVec.reduceLeft((_:Double)+(_:Double)),0.5)
	 val rightVec = for(i<- 0 to vectorSize) yield{
	    v2.get(i)*v2.get(i)
	  }
	 val rightDenom = Math.pow(leftVec.reduceLeft((_:Double)+(_:Double)),0.5)
	 return numerator/(leftDenom*rightDenom)
	}
}