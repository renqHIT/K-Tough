import org.apache.spark.graphx.{VertexId, VertexRDD, Graph, GraphLoader}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.{RDD, PairRDDFunctions}

object Sampling {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Toughness-5.12")
      .setMaster("spark://cluster01:7077")
      .set("spark.executor.memory", "6g")
    val sc = new SparkContext(conf)
    val dataDir = "/home/renq/k-tough/data"
    val data: Graph[Int, Int] = GraphLoader.edgeListFile(sc, s"$dataDir/roadNet-CA.txt").cache() // an RDD[(K, V)] of any key value pairs
    val degrees: VertexRDD[Int] = data.degrees

    val users: RDD[(Int, Int)] = sc.parallelize(Array((0, 1), (0, 2), (0, 469), (1, 0), (1, 6), (1, 385),
      (2, 0), (2, 3), (469, 0), (469, 380), (469, 37415), (6, 1), (6, 5), (385, 1),
      (385, 384), (385, 386), (3, 2), (3, 4), (3, 419), (3, 422)))

    val fraction: Double = 0.8
    val fractions = users.keys.map((_, fraction)).collect().toMap // specify the exact fraction desired from each key
    // Get an exact sample from each stratum
    val approxSample: RDD[(Int, Int)] = users.sampleByKey(withReplacement = false, fractions, seed = 1L)
    print(approxSample.collect())
    //val exactSample = data.sampleByKeyExact(withReplacement = false, fractions)
  }
}