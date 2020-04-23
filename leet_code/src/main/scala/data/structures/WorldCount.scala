package data.structures

import org.apache.spark.{SparkConf, SparkContext}

object WorldCount {
  def main(args: Array[String]): Unit = {
     val conf = new SparkConf()
       .setAppName("新项目的测试")
       .setMaster("local[2]")
    val sc = new SparkContext(conf)
    println("worldcount")
    sc.stop()
  }
}
