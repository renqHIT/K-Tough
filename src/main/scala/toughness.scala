import org.apache.spark.SparkContext  
import org.apache.spark.SparkContext._  
import org.apache.spark.SparkConf  
import scala.collection.mutable.Map  
import org.apache.spark._  
import org.apache.spark.graphx._  
import org.apache.spark.graphx.GraphOps
import org.apache.spark.rdd.RDD 

object Toughness{
  def main(args:Array[String]){
    //val dataDir = "hdfs://cluster01:9090/usr/renq/k-tough/data"
    //val dataDir = "hdfs://usr/renq/k-tough/data"
    val dataDir = "/home/renq/k-tough/data"
    //val conf = new SparkConf().setMaster("spark://cluster01:7077").setAppName("Toughness").set("spark.executor.memory", "6g")
    //val sc = new SparkContext(conf)
    val sc = new SparkContext("local", "Toughness", "127.0.0.1", List("target/scala-2.10/toughness_2.10-1.0.jar"))
    val road:Graph[Int, Int] = GraphLoader.edgeListFile(sc, s"$dataDir/roadNet-CA.txt")
    val testCases = List("eg1", "eg2", "eg3", "circle", "line", "star", "complete")
    for (test <-testCases){
      println(s"Running Graph: $test")
      var tough = 1.0
      val eg = GraphLoader.edgeListFile(sc, s"$dataDir/$test.txt")
      //println("Graph size" + eg.connectedComponents.vertices.collect().toList.size)
      val combine_eg:List[List[VertexId]] = eg.vertices.collect().toList.map(kv => kv._1).toSet[VertexId].subsets.map(_.toList).toList
      var seperator:collection.mutable.Set[VertexId] = collection.mutable.Set()
      val sTime = System.nanoTime
      eg.numVertices match {
        case x if x < 32 => {
          tough = 1.0
          for(cs <- combine_eg){
            seperator = collection.mutable.Set()
            for(cx <- cs){
              seperator += cx
            }
            val sub_eg = eg.subgraph(vpred = (vid, attr) => !(seperator contains vid))
            val ccl = sub_eg.connectedComponents.vertices.collect().toList.map(t=>t._2) 
            val cc = ccl.toSet.size
            //println("seperator: "+seperator)
            //println("cc: %d".format(cc))
            if(seperator.size != 0 && cc != 0){
              tough = math.min(tough, seperator.size.toFloat/cc)
            }
          }
          tough
        }
        case x if x > 32 => {
          seperator = collection.mutable.Set()
          for(i <- 1 to 4){
            seperator += eg.pickRandomVertex()
            val sub_eg = eg.subgraph(vpred = (vid, attr) => seperator contains vid)
            val cc = sub_eg.connectedComponents.vertices.collect().toList.size
            tough = seperator.size.toFloat/cc
          }
        }
        case _ => Nil
      }
      val eTime = System.nanoTime
      val micros = (eTime - sTime)/1000
      println("%d microseconds".format(micros))
      println(s"Toughness of $test: %f".format(tough))
      tough
    }
  }
}
