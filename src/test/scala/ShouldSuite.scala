import org.scalatest.FunSpec import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

class ToughTest extends FlatSpec with ShouldMatchers{
  val dataDir = "hdfs://usr/renq/k-tough/data"
  val conf = new SparkConf().setMaster("spark://cluster01:7077").setAppName("Toughness Test").set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)
  "The toughness calculator" should
    "return 1/(n-1) when calculating a star graph" in
    {
      val star = GraphLoader.edgeListFile(sc, s"$dataDir/star.txt")
      //toughness(star) should be 
    }
    it should "return 1/2 when calculating a line graph" in 
    {
      val line = GraphLoader.edgeListFile(sc, s"$dataDir/line.txt")
      //toughness(line) should be (0.5)
    }
    it should "return 1 when calculating a circle graph" in
    {
      val circle = GraphLoader.edgeListFile(sc, s"$dataDir/circle.txt")
      //toughness(circle) should be (1)
    }
    it should "return infinity when calculating a complete graph" in 
    {
      val complete = GraphLoader.edgeListFile(sc, s"$dataDir/complete.txt")
      //toughness(complete) should be infinity
    }
    it should "return 0.25 with running example 1" in
    {
      val eg1 = GraphLoader.edgeListFile(sc, s"$dataDir/eg1.txt")
      //toughness(eg1) should be 0.25
    }
    it should "return 0.5 with running example 2" in 
    {
      val eg2 = GraphLoader.edgeListFile(sc, s"$dataDir/eg2.txt")
      //toughness(eg2) should be 0.5
    }
    it should "give reasonable toughness with roadNet-CA" in
    {
      val road = GraphLoader.edgeListFile(sc, s"$dataDir/roadNet-CA.txt")
      //toughness(road) should be ???
    {
      //val t = 0.5
      //val data = new graph()
      //val subgraphs = finder(data, t)
    }
    it should "return an empty list when having no subgraphs with toughess greater than t" in 
    {
      //val t = 100.0
      //val data = new graph()
      //val subgraphs = finder(data, t)
    }
}
