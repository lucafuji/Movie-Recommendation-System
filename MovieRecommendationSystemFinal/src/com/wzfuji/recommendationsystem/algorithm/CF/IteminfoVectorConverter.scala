package com.wzfuji.recommendationsystem.algorithm.CF
import com.wzfuji.db.entity.Iteminfo
import com.wzfuji.util.ReflectionUtils
import org.apache.mahout.math._
import scala.collection.JavaConversions._

/**
 * @author lucafuji
 * convert a list of double to a vector
 */
object IteminfoVectorConverter {
	
	def toVector(seq:List[Double])= {
	   new DenseVector(seq.toArray)
	}
}