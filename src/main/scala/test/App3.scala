package test


import org.apache.avro.{Schema}
import org.apache.avro.generic.{ GenericData}


object App3 {


  def main(args: Array[String]): Unit = {

    var parser = new Schema.Parser();
    var schema = parser.parse(getClass().getResourceAsStream("/stringpair.avsc"))
    var datum = new GenericData.Record(schema);
    datum.put("left", "L");
    datum.put("right", "R");
    println(datum)


  }

}
