import java.io.{File, FileNotFoundException}
import org.apache.commons.io.FileUtils
import scala.io.Source


object Partitions{
  def main (args: Array[String]) {
    //val dataDir = "/home/renq/k-tough/data"
    //val filename = "data/roadNet-CA.txt"
    val filename = "data/roadNet-CA.txt"
    val numParts = 4
    val numStrategies = 4
    val output: Array[File] = new Array[File](numParts * numStrategies)

    //EdgePartition2D
    println("Start Partitioning: EdgePartition2D")
    for( i <- 0 to numParts-1 ) {
      output(i) = new File(s"data/partitions/EdgePartition2D/part-$i.txt")
    }
    try {
      for (line <- Source.fromFile(filename).getLines()) {
        if(line.startsWith("#")){
          //println(line)
        }else {
          val src: String = line.split("\\s+")(0)
          val dst: String = line.split("\\s+")(1)
          val ceilSqrtNumParts = math.ceil(math.sqrt(numParts)).toInt
          val mixingPrime = 1125899906842597L
          val col = (math.abs(src.toInt * mixingPrime) % ceilSqrtNumParts).toInt
          val row = (math.abs(dst.toInt * mixingPrime) % ceilSqrtNumParts).toInt
          val partitionTo = (col * ceilSqrtNumParts + row) % numParts
          //println(src, dst, partitionTo)
          FileUtils.writeStringToFile(output(partitionTo), src+", "+dst+'\n', true)
        }
      }
    } catch {
      case ex: FileNotFoundException => println("FileNotFoundException.")
      case ex: ArrayIndexOutOfBoundsException => println("ArrayIndexOutOfBoundsException")
    }

    //EdgePartition1D
    println("Start Partitioning: EdgePartition1D")
    for( i <- 0 to numParts-1 ) {
      output(i + 4) = new File(s"data/partitions/EdgePartition1D/part-$i.txt")
    }
    try {
      for (line <- Source.fromFile(filename).getLines()) {
        if(line.startsWith("#")){
          //println(line)
        }else {
          val src: String = line.split("\\s+")(0)
          val dst: String = line.split("\\s+")(1)
          val mixingPrime: Long = 1125899906842597L
          val partitionTo = (math.abs(src.toInt * mixingPrime) % numParts).toInt
          FileUtils.writeStringToFile(output(partitionTo + 4), src+", "+dst+'\n', true)
        }
      }
    } catch {
      case ex: FileNotFoundException => println("FileNotFoundException.")
      case ex: ArrayIndexOutOfBoundsException => println("ArrayIndexOutOfBoundsException")
    }

    //RandomVertexCut
    println("Start Partitioning: RandomVertexCut")
    for( i <- 0 to numParts-1 ) {
      output(i + 8) = new File(s"data/partitions/RandomVertexCut/part-$i.txt")
    }
    try {
      for (line <- Source.fromFile(filename).getLines()) {
        if(line.startsWith("#")){
          //println(line)
        }else {
          val src: String = line.split("\\s+")(0)
          val dst: String = line.split("\\s+")(1)
          val partitionTo = math.abs((src.toInt, dst.toInt).hashCode()) % numParts
          FileUtils.writeStringToFile(output(partitionTo + 8), src+", "+dst+'\n', true)
        }
      }
    } catch {
      case ex: FileNotFoundException => println("FileNotFoundException.")
      case ex: ArrayIndexOutOfBoundsException => println("ArrayIndexOutOfBoundsException")
    }

    //CanonicalRandomVertexCut
    println("Start Partitioning: CanonicalRandomVertexCut")
    for( i <- 0 to numParts-1 ) {
      output(i + 12) = new File(s"data/partitions/CanonicalRandomVertexCut/part-$i.txt")
    }
    try {
      for (line <- Source.fromFile(filename).getLines()) {
        if(line.startsWith("#")){
          //println(line)
        }else {
          val src: String = line.split("\\s+")(0)
          val dst: String = line.split("\\s+")(1)
          if (src < dst) {
            val partitionTo = math.abs((src.toInt, dst.toInt).hashCode()) % numParts
            FileUtils.writeStringToFile(output(partitionTo + 12), src+", "+dst+'\n', true)
          } else {
            val partitionTo = math.abs((dst.toInt, src.toInt).hashCode()) % numParts
            FileUtils.writeStringToFile(output(partitionTo + 12), src+", "+dst+'\n', true)
          }
        }
      }
    } catch {
      case ex: FileNotFoundException => println("FileNotFoundException.")
      case ex: ArrayIndexOutOfBoundsException => println("ArrayIndexOutOfBoundsException")
    }
    println("Finish! ")
  }
}