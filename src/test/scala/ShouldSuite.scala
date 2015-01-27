package org.hit.renq.toughness

import org.scalatest.FunSpec 
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.rdd.RDD

class ToughTest extends FlatSpec with ShouldMatchers{
  //val dataDir = "hdfs://cluster01:9090/usr/renq/k-tough/data"
  val dataDir = "/home/renq/k-tough/data"
  //val conf = new SparkConf().setMaster("spark://cluster01:7077").setAppName("Toughness Test").set("spark.executor.memory", "6g")
  //val sc = new SparkContext(conf)
  val sc = new SparkContext("local", "Toughness", "127.0.0.1", List("target/scala-2.10/toughness_2.10-1.0.jar"))
  val calculator = new Toughness
  "The toughness calculator" should
  "return 1/(n-1) when calculating a star graph" in
  {
    //val star = GraphLoader.edgeListFile(sc, s"$dataDir/star.txt")
    //val star = GraphGenerators.starGraph(sc, 6)
    //val n = 5
    //val star = GraphGenerators.starGraph(sc, n)
    //val nodes = sc.parallelize(List(0L, 1L, 2L, 3L, 4L, 5L))
    //val edges = sc.parallelize(List(Edge(0L,1L,1), Edge(0L,2L,1), Edge(0L,3L,1), Edge(0L,4L,1), Edge(0L,5L,1), 5))
    //val star = Graph(nodes, edges)
    val star = Graph(
      sc.parallelize(List((0L,1), (1L,1), (2L,1), (3L,1), (4L,1), (5L,1))),
    sc.parallelize(List(Edge(0L,1L,1), Edge(0L,2L,1), Edge(0L,3L,1), Edge(0L,4L,1), Edge(0L,5L,1)), 5))
    calculator.toughness(star) should be (0.2)
  }
  it should "return 1/2 when calculating a line graph" in 
  {
    val line = GraphLoader.edgeListFile(sc, s"$dataDir/line.txt")
    //val line = Graph(sc.parallelize(List((0L,"a"), (1L, "b"), (2L, "c"))), 
    //    sc.parallelize(List(Edge(0L, 1L, 1), Edge(0L, 2L, 1), 2)))
    //calculator.toughness(line) should be (0.5)
  }
  it should "return 1 when calculating a circle graph" in
  {
    val circle = GraphLoader.edgeListFile(sc, s"$dataDir/circle.txt")
    //calculator.toughness(circle) should be (1)
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
  }
  it should "give reasonable toughness with lognormal graph" in
  {
    //val lognormal = GraphGenerators.logNormalGraph(sc, numVertices = 100, numEParts = 0, mu = 4.0, sigma = 1.3)
    //toughness(lognormal) should be ???
  }
  it should "give reasonable toughness with RMAT graph" in 
  {
    val rmat = GraphGenerators.rmatGraph(sc, 10, 50)
    //toughness(rmat) should be ???
  }

  "The toughness finder" should 
  "return a list of subgraphs with toughness less than 0.5" in 
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
  it should "perform well on lognormal graph" in
  {
    //val lognormal = GraphGenerators.logNormalGraph(sc, numVertices = 100, numEParts = 0, mu = 4.0, sigma = 1.3)
    //toughness(lognormal) should be ???
  }
  it should "perform well on RMAT graph" in 
  {
    val rmat = GraphGenerators.rmatGraph(sc, 10, 50)
    //toughness(rmat) should be ???
  }
}
