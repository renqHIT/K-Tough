package renq.tough
import org.apache.spark.SparkContext._
import org.apache.spark.graphx.PartitionStrategy
import org.apache.spark.graphx.PartitionStrategy._
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.{SparkContext, SparkConf}
import scala.collection.mutable.Map
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import java.io.{PrintWriter, FileOutputStream}

object Operator{
  def main(args:Array[String]){
	val partitionNames:List[PartitionStrategy] = List(RandomVertexCut, EdgePartition1D, EdgePartition2D, CanonicalRandomVertexCut)
	  var niter = 10
	  var numVertices = 1000
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
	  val e1d = graph.partitionBy(EdgePartition1D, 4)
	  val e2d = graph.partitionBy(EdgePartition2D, 4)
	  val canonical = graph.partitionBy(CanonicalRandomVertexCut, 4)
	  val rvert = graph.partitionBy(RandomVertexCut, 4)

	  for(pa <- partitionNames){
		println(s"Partition by $pa")
		  var pstart = System.currentTimeMillis()
		  graph.partitionBy(pa, 4).collectNeighbors(EdgeDirection.Either)
		  val partitionTime = System.currentTimeMillis - pstart
		  println(s"Partition time = ${partitionTime/1000.0} seconds")
	  }

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
	  var toughness = graph.staticPageRank(niter).vertices.map(_._2).sum()
	  var runTime = System.currentTimeMillis() - startTime
	  println(s"Toughness logNormal is $toughness")
	  println(s"Run time = ${runTime/1000.0} seconds")

	/*
	  val dataDir = "/home/spark/K-Tough/data"
	  val eg = GraphLoader.edgeListFile(sc, s"$dataDir/com-dblp.txt").cache()
	  startTime = System.currentTimeMillis()
	  toughness = eg.staticPageRank(niter).vertices.map(_._2).sum()
	  runTime = System.currentTimeMillis() - startTime
	  println(s"Toughness com-dblp is $toughness")
	  println(s"Run time = ${runTime/1000.0} seconds")
	  eg.unpersistVertices()
	*/
	  sc.stop()
  }
}
