package com.wzfuji.math
import junit.framework.TestCase
import junit.framework.Assert.assertEquals
import junit.framework.Assert._
import org.apache.mahout.math.DenseVector
class DistanceMeasureTest  extends TestCase{
	def testAdjustedCosineDistance(){
	  val value1 = Array[Double](2,2)
	  val value2 = Array[Double](2,2)
	  val avgValue = Array[Double](1,1)
	  val v1 = new DenseVector(value1)
	  val v2 = new DenseVector(value2)
	  val avgVector = new DenseVector(avgValue)
	  val distanceMeasure = new AdjustCosineDistance(avgVector)
	  assertEquals(1.0,distanceMeasure.computeDistance(v1,v2))
	}
}