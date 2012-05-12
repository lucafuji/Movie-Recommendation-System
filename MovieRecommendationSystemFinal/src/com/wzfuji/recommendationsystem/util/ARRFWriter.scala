package com.wzfuji.recommendationsystem.util
import java.io.File
import com.wzfuji.util.FileUtils._
import scala.collection.JavaConversions._

/**
 * @author lucafuji
 * write the data object to an ARRF file required by weka
 */
class ARRFWriter(fileName:String,relationName:String,var attrs:java.util.Map[java.lang.String,java.lang.String],targetAttr:(String,String)) {
  val file = new File(fileName)
  appendToFile(file,"@Relation "+relationName+"\n\n")
  val attributes = attrs.toMap
  for((attributeName,typeName)<-attributes){
     appendToFile(file,"@ATTRIBUTE "+attributeName+" "+typeName+"\n")
  }
  if(targetAttr!=null){
    appendToFile(file,"@ATTRIBUTE "+targetAttr._1+" "+targetAttr._2+"\n")
  }
  appendToFile(file,"\n")
  appendToFile(file,"@DATA\n")
  def appendInstance(instance:Any,targetValue:Any){
    var rep:String = ""
    var clazz = instance.getClass()
    for((attributeName,_)<-attributes){
      val field = clazz.getDeclaredField(attributeName)
      field.setAccessible(true)
      rep+=field.get(instance)+","
    }
    if(targetValue!=null){
      rep+=targetValue
    }else{
      rep =rep.drop(rep.length()-1)
    }
    appendToFile(file,rep+"\n")
  }
  
  def close(){
    file.delete()
  }
  
  def getFile() = file
}