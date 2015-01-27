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
    val eg1 = GraphLoader.edgeListFile(sc, s"$dataDir/eg1.txt")
    val combine_eg1 = eg1.vertices.collect().toList.map(kv => kv._1).toSet[VertexId].subsets.map(_.toList).toList
    //val rand_vertex = eg1.GraphOps.pickRandomVertex()
    val seperator:collection.mutable.Set[VertexId] = collection.mutable.Set()
    for(i <- 1 to 4){
      seperator += eg1.pickRandomVertex()
    }
    val sub_eg = eg1.subgraph(vpred = (vid, attr) => seperator contains vid)
    val project_eg = eg1.mask(sub_eg)
    //val neighbors = GraphOps.collectNeighborIds()
    //val inDegrees = graph.inDegrees
    //val degrees : VertexRDD[Int] = graph.degrees
    //val vertices = graph.vertices
    //val edges = graph.edges
    //val triplets = graph.triplets

    //2.compute toughness for vertices above
    //3.learn the relation between vertex degree and toughness, using, say linear regression
  }
}

