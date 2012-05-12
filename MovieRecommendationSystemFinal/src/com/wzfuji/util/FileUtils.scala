package com.wzfuji.util
import java.io.FileWriter
import java.io.PrintWriter
import java.io.File

object FileUtils {
  def using[A <: { def close(): Unit }, B](param: A)(f: A => B): B =
    try { f(param) } finally { param.close() }

  /**
   * append the text to the file
   * @param file
   * @param textData
   */
def appendToFile(file: File, textData: String) =
    using(new FileWriter(file, true)) {
      fileWriter =>
        using(new PrintWriter(fileWriter)) {
          printWriter => printWriter.println(textData)
        }
    }

}