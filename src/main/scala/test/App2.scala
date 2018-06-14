package test

import org.apache.avro.Schema
import org.apache.avro.generic.{GenericData,GenericDatumReader, GenericDatumWriter, GenericRecord}
import java.io.ByteArrayOutputStream

import org.apache.avro.io.EncoderFactory
import org.apache.avro.io.DecoderFactory
import org.apache.spark._
import org.apache.spark.streaming._


object App2 {

  def main(args: Array[String]) = {


    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(5))

    val lines = ssc.socketTextStream("localhost", 9999)

    lines.foreachRDD(t=>{

      t.map[String](s=>{
        var parser = new Schema.Parser();
        var schema = parser.parse(getClass().getResourceAsStream("/stringpair.avsc"))
        val out = new ByteArrayOutputStream()
//        val encoder = EncoderFactory.get.jsonEncoder(schema,out)
        val encoder = EncoderFactory.get.binaryEncoder(out,null)

        val writer = new GenericDatumWriter[GenericRecord](schema)

        var datum = new GenericData.Record(schema);
        datum.put("left", s);
        datum.put("right",s);

        writer.write(datum, encoder)
        encoder.flush
        out.close
        var bb = out.toString
        println(bb.length)
        bb.foreach(f=>{
          print(f+":")
        })
        println()
        bb


      }).pipe("python test.py -u ").foreach(

        r=>{

        var parser = new Schema.Parser();
        var schema = parser.parse(getClass().getResourceAsStream("/stringpair.avsc"))

        val decoder = DecoderFactory.get.binaryDecoder(r.getBytes(), null)

        val reader = new GenericDatumReader[GenericRecord](schema)
        val record = reader.read(null, decoder)
        println(record)

      }

      )

    });


    //    val lines = ssc.fileStream(System.getProperty("user.dir"),x=>x.getName.endsWith("xml"), false)

    //    lines.print()

    //    lines.foreachRDD(t=>{
    //      val words = t.flatMap(_.split(" "))
    //
    //      val pairs = words.map(word => (word, 1))
    //      val wordCounts = pairs.reduceByKey(_ + _)
    //      wordCounts.pipe("sh test1.sh").foreach(println)
    //
    //    })

    // Print the first ten elements of each RDD generated in this DStream to the console


    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate

  }

}
