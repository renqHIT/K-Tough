import org.apache.spark.SparkContext  
import org.apache.spark.SparkContext._  
import org.apache.spark.SparkConf  
import scala.collection.mutable.Map  
import org.apache.spark._  
import org.apache.spark.graphx._  
import org.apache.spark.rdd.RDD 

object Toughness{
  def main(args:Array[String]){
    //val dataDir = "hdfs://cluster01:9090/usr/renq/k-tough/data"
    //val dataDir = "hdfs://usr/renq/k-tough/data"
    val dataDir = "/home/renq/k-tough/data"
    //val conf = new SparkConf().setMaster("spark://cluster01:7077").setAppName("Toughness").set("spark.executor.memory", "6g")
    //val sc = new SparkContext(conf)
    val sc = new SparkContext("local", "Toughness", "127.0.0.1", List("target/scala-2.10/toughness_2.10-1.0.jar"))
    val graph:Graph[Int, Int] = GraphLoader.edgeListFile(sc, s"$dataDir/roadNet-CA.txt")
    val inDegrees = graph.inDegrees
    val degrees : VertexRDD[Int] = graph.degrees
    val vertices = graph.vertices
    val edges = graph.edges
    val triplets = graph.triplets
    //save partitions of graph into local file system
    //vertices.saveAsTextFile("/home/hadoop/partition/default-partitions")
    //1.find vertices with degree higher than 10
    val vertice5 = vertices.filter{ case (d : (VertexId, Int)) => d._1 >= 1970000}
    println("Vertices with ID higher than 1970000: " + vertice5.count())
    //vertice5.collect.foreach(println(_))
    println("graph.degrees type is: " + degrees.getClass)
    val degree10 = degrees.filter{ case (d : (VertexId,Int)) => d._2 >= 10 }
    //degree10.collect.foreach(println(_))

    //println(debugString)
    //degree10.collect.foreach(println(_))
    //2.compute toughness for vertices above
    //3.learn the relation between vertex degree and toughness, using, say linear regression
  }
}
