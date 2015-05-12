import java.io.{File, PrintWriter, FileNotFoundException}

import scala.io.Source
import org.apache.spark.graphx.{PartitionID, VertexId, PartitionStrategy}

object Partitions{
  def main (args: Array[String]) {
    //val dataDir = "/home/renq/k-tough/data"
    //val filename = "data/roadNet-CA.txt"
    val filename = "data/eg1.txt"
    val numParts: Int = 4

    try {
      for (line <- Source.fromFile(filename).getLines()) {
        if(line.startsWith("#")){
          println(line)
        }else {
          val writer = new PrintWriter(new File("data/partitions/EdgePartition2D/test1.txt" ))
          val src: String = line.split("\\s+")(0)
          val dst: String = line.split("\\s+")(1)
          val ceilSqrtNumParts = math.ceil(math.sqrt(numParts)).toInt
          val mixingPrime = 1125899906842597L
          val col = (math.abs(src.toInt * mixingPrime) % ceilSqrtNumParts).toInt
          val row = (math.abs(dst.toInt * mixingPrime) % ceilSqrtNumParts).toInt
          val partitionTo = (col * ceilSqrtNumParts + row) % numParts
          println(src, dst, partitionTo)
          writer.write("Hello Scala")
          writer.close()
        }
      }
    } catch {
      case ex: FileNotFoundException => println("FileNotFoundException.")
      case ex: ArrayIndexOutOfBoundsException => println("ArrayIndexOutOfBoundsException")
    }

    /*
    case object EdgePartition2D {
      override def getPartition(src: VertexId, dst: VertexId): PartitionID = {
        val ceilSqrtNumParts: PartitionID = math.ceil(math.sqrt(numParts)).toInt
        val mixingPrime: VertexId = 1125899906842597L
        val col: PartitionID = (math.abs(src * mixingPrime) % ceilSqrtNumParts).toInt
        val row: PartitionID = (math.abs(dst * mixingPrime) % ceilSqrtNumParts).toInt
        (col * ceilSqrtNumParts + row) % numParts
      }
    }

    /**
     * Assigns edges to partitions using only the source vertex ID, colocating edges with the same
     * source.
     */
    case object EdgePartition1D{
      override def getPartition(src: VertexId, dst: VertexId): PartitionID = {
        val mixingPrime: VertexId = 1125899906842597L
        (math.abs(src * mixingPrime) % numParts).toInt
      }
    }


    /**
     * Assigns edges to partitions by hashing the source and destination vertex IDs, resulting in a
     * random vertex cut that colocates all same-direction edges between two vertices.
     */
    case object RandomVertexCut {
      override def getPartition(src: VertexId, dst: VertexId): PartitionID = {
        math.abs((src, dst).hashCode()) % numParts
      }
    }


    /**
     * Assigns edges to partitions by hashing the source and destination vertex IDs in a canonical
     * direction, resulting in a random vertex cut that colocates all edges between two vertices,
     * regardless of direction.
     */
    case object CanonicalRandomVertexCut{
      override def getPartition(src: VertexId, dst: VertexId): PartitionID = {
        if (src < dst) {
          math.abs((src, dst).hashCode()) % numParts
        } else {
          math.abs((dst, src).hashCode()) % numParts
        }
      }
    }
    */
  }
}