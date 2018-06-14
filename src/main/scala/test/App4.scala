package test

import org.apache.spark.{SparkConf, SparkContext}

object App4 {

  def main(args: Array[String]): Unit = {


    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    var sc = new SparkContext(conf)

    var file = sc.textFile(System.getProperty("user.dir") + "/pom.xml")

    var rdd = file.flatMap(line => line.split("<>\\s*:")).pipe("sh test1.sh").pipe("sh test2.sh").pipe("sh test3.sh").foreach(println)



  }

}
