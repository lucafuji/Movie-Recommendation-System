package com.wzfuji.recommendationsystem.experiment
import com.wzfuji.recommendationsystem.instance.RSInstance
import scala.io.Source
/**
 * @author lucafuji
 * reader instance from the training and test file 
 */
object RSIntanceReader {
	def readRSInstance(fileName:String):Iterator[RSInstance] ={
	  val reader = Source.fromFile(fileName)
	   for(line<-reader.getLines()) yield{
	    val args = line.split("\\s+");
	    new RSInstance(args(0).toInt,args(1).toInt,args(2).toDouble)
	  }
	}
}