import org.scalatest.FunSpec 
import org.scalatest.FlatSpec
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.graphx.util.GraphGenerators
import org.apache.spark.rdd.RDD

object SparkTest extends org.scalatest.Tag("org.hit.renq.tags.SparkTest")

trait SparkTestUtilB extends FunSuite {
  var sc: SparkContext = _

  def sparkTest(name: String)(body: => Unit) {
    test(name, SparkTest){
      sc = new SparkContext("local[2]", name)
      try {
        body
      }
      finally {
        sc.stop
        sc = null
        System.clearProperty("spark.master.port")
      }
    }
  }
}

object SparkUtil {
  class OurAwesomeClassTest extends SparkTestUtilB with ShouldMatchers {
    val dataDir = "/home/renq/k-tough/data"
    sparkTest("spark filter") {
      val line = GraphLoader.edgeListFile(sc, s"$dataDir/line.txt")
    }

    test("non-spark code") {
      val x = 17
      val y = 3
      20 should be (20)
    }
  }
}
