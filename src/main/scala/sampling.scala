import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.PairRDDFunctions

val sc: SparkContext = ...

val data = ... // an RDD[(K, V)] of any key value pairs
val fractions: Map[K, Double] = ... // specify the exact fraction desired from each key

// Get an exact sample from each stratum
// val approxSample = data.sampleByKey(withReplacement = false, fractions)
// val exactSample = data.sampleByKeyExact(withReplacement = false, fractions)
