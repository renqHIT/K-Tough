package renq.tough 

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._  
import org.apache.spark.SparkConf  
import scala.collection.mutable.Map  
import org.apache.spark._  
import org.apache.spark.graphx._  
import org.apache.spark.graphx.GraphOps
import org.apache.spark.rdd.RDD 

class Toughness{
  def toughness(graph: Graph[PartitionID, PartitionID]) = {
    var tough = 1.0
    //println("Graph size" + eg.connectedComponents.vertices.collect().toList.size)
    val combine_eg:List[List[VertexId]] = graph.vertices.collect().toList.map(kv => kv._1).toSet[VertexId].subsets.map(_.toList).toList
    var seperator:collection.mutable.Set[VertexId] = collection.mutable.Set()

    graph.numVertices match {
      case x if x < 32 => {
        tough = 1.0
        for(cs <- combine_eg){
          seperator = collection.mutable.Set()
          for(cx <- cs){
            seperator += cx
          }
          val sub_eg = graph.subgraph(vpred = (vid, attr) => !(seperator contains vid))
          //val numComponents = eg.connectedComponents.vertices.map(_._2).distinct().count()
          val ccl = sub_eg.connectedComponents.vertices.collect().toList.map(t=>t._2)
          val numComponents = ccl.toSet.size
          //println("seperator: "+seperator)
          //println(s"numCompunents is $numCompunents")
          if(seperator.size != 0 && numComponents != 0){
            tough = math.min(tough, seperator.size.toFloat/numComponents)
          }
        }
      }
      case x if x > 32 => {
        //println(s"Running $test")
        tough = 1.0
        for(i <- 1 to 1){
          seperator = collection.mutable.Set()
          for(j <- 1 to i){
            seperator += graph.pickRandomVertex()
          }
          val sub_eg = graph.subgraph(vpred = (vid, attr) => !(seperator contains vid))
          //val numComponents = eg.connectedComponents.vertices.map(_._2).distinct().count()
          val ccl = sub_eg.connectedComponents.vertices.collect().toList.map(t=>t._2)
          val numComponents = ccl.toSet.size
          //println("seperator: "+seperator)
          //println(s"numCompunents is $numComponents")
          if(seperator.size != 0 && numComponents != 0){
            tough = math.min(tough, seperator.size.toFloat/numComponents)
          }
        }
      }
      case _ => Nil
    }
    tough
  }

  def main(args:Array[String]){
    val dataDir = "/home/renq/k-tough/data"
    val conf = new SparkConf().setAppName("Toughness-3.14")
      .setMaster("spark://cluster01:7077")
      .set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)
    //val sc = new SparkContext("local", "Toughness", "127.0.0.1", List("target/scala-2.10/toughness_2.10-1.0.jar"))
    val testCases = List("eg1", "eg2", "eg3", "circle", "line", "star", "complete")//, "roadNet-CA")
    //val testCases = List("roadNet-CA")
    println("Data\tToughness\tRunning time")
    for (test <-testCases){
      println(s"Running Graph: $test")
      //RDDs are not persisted in memory by default.
      //When using a graph multiple times, make sure to cache it first!
      val eg = GraphLoader.edgeListFile(sc, s"$dataDir/$test.txt").cache()
      val startTime = System.currentTimeMillis()
      val t = toughness(eg)
      val runTime = System.currentTimeMillis() - startTime
      println(s"$test\t$t\t${runTime/1000.0}")
      eg.unpersistVertices()
      }
    sc.stop()
  }
}
