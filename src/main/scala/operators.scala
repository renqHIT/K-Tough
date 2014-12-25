import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf
import scala.collection.mutable.Map
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

object Operator{
  def main(args:Array[String]){
    val conf = new SparkConf().setMaster("local").setAppName("Toughness").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)
    val graph = GraphLoader.edgeListFile(sc, "./data/roadNet-CA.txt")
    //println graph.vertices
  }
}
