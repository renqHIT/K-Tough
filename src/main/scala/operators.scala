import org.apache.spark.SparkContext._
import org.apache.spark.graphx.PartitionStrategy
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.{SparkContext, SparkConf}
import scala.collection.mutable.Map
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import java.io.{PrintWriter, FileOutputStream}

object Operator{
  def main(args:Array[String]){
    val partitionNames = List("RandomVertexCut", "EdgePartition1D", "EdgePartition2D", "CanonicalRandomVertexCut")
    var niter = 10
    var numVertices = 100
    var numEPart: Option[Int] = None
    var partitionStrategy: Option[PartitionStrategy] = None
    var mu: Double = 4.0
    var sigma: Double = 1.3
    var degFile: String = ""
    var seed: Int = -1

    val conf = new SparkConf().setMaster("local").setAppName("LogNormal").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)
    println("Creating graph...")
    val unpartitionedGraph = GraphGenerators.logNormalGraph(sc, numVertices, numEPart.getOrElse(sc.defaultParallelism), mu, sigma)

    // Repartition the graph
    val graph = partitionStrategy.foldLeft(unpartitionedGraph)(_.partitionBy(_)).cache()
    var startTime = System.currentTimeMillis()
    val numEdges = graph.edges.count()
    println(s"Done creating graph. Num Vertices = $numVertices, Num Edges = $numEdges")
    val loadTime = System.currentTimeMillis() - startTime

    // Collect the degree distribution (if desired)
    if (!degFile.isEmpty) {
      val fos = new FileOutputStream(degFile)
      val pos = new PrintWriter(fos)
      val hist = graph.vertices.leftJoin(graph.degrees)((id, _, optDeg) => optDeg.getOrElse(0))
        .map(p => p._2).countByValue()
        hist.foreach {
          case (deg, count) => pos.println(s"$deg \t $count")
        }
    }

    // Run Toughness
    startTime = System.currentTimeMillis()
    println("Running Toughness")
    //val toughness = //graph.staticPageRank(niter).vertices.map(_._2).sum()
    //println(s"Toughness is $toughness")
    val runTime = System.currentTimeMillis() - startTime

    println(s"Num Vertices = $numVertices")
    println(s"Num Edges = $numEdges")
    println(s"Creation time = ${loadTime/1000.0} seconds")
    println(s"Run time = ${runTime/1000.0} seconds")

    sc.stop()
  }
}
