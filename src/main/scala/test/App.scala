package test

import java.io._
import org.apache.spark.{SparkConf, SparkContext}


/**
  * @author ${user.name}
  */
object App {

  def foo(x: Array[String]) = x.foldLeft("")((a, b) => a + b)

  def main(args: Array[String]) {
    var conf = new SparkConf().setMaster("local").setAppName("mytest")
    var sc = new SparkContext(conf)
    var file =  sc.textFile(System.getProperty("user.dir")+"/pom.xml")

//    var rdd =
    var rdd = file.flatMap(line=>line.split("<>\\s*:")).map(word=>(word,1)).groupByKey(4).pipe("python test.py")
    rdd.collect()
    rdd.foreach(println)
  }

}
