package com.wzfuji.math

import org.apache.mahout.math.Vector
import scala.Math
/**
 * @author lucafuji
 * adjusted cosine distance of two vectors, modified cosine distance by subtracting
 * each element in the vector by the average rating of the user
 */
class AdjustCosineDistance(val averageUserRating:Vector) extends DistanceMeasure{
	override def computeDistance(v1:Vector,v2:Vector):Double={
	  val vectorSize = v1.size()
	  val leftVector = for(i<- 0 to vectorSize) yield {
	    v1.get(i)-averageUserRating.get(i)
	  }
	  
	  val rightVector = for(i<- 0 to vectorSize) yield {
	    v2.get(i)-averageUserRating.get(i)
	  }
	    
	  val numeratorVector = for(tuple<- leftVector zip rightVector) yield{
	    tuple._1*tuple._2
	  }
	  val numerator = numeratorVector.reduceLeft((_:Double)+(_:Double))
	  val leftDenomitorVector = for(elem<- leftVector) yield{
	    elem * elem
	  }
	  val leftDenomitor = Math.pow(leftDenomitorVector.reduceLeft((_:Double)+(_:Double)),0.5)
	  
	  val rightDenomitorVector = for(elem<- leftVector) yield{
	    elem * elem
	  }
	  val rightDenomitor = Math.pow(leftDenomitorVector.reduceLeft((_:Double)+(_:Double)),0.5)
	  numerator/(leftDenomitor*rightDenomitor) 
	}
}