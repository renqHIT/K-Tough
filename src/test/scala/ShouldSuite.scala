import org.scalatest.FunSpec
import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class ToughTest extends FlatSpec with ShouldMatchers{
  "The toughness calculator" should
    "return 1/(n-1) when calculating a star graph" in
    {
      //val star = new graph()
      //toughness(star) should be 
    }
    it should "return 1/2 when calculating a line graph" in 
    {
      //val line = new graph()
      //toughness(line) should be (0.5)
    }
    it should "return 1 when calculating a circle graph" in
    {
      //val circle = new graph()
      //toughness(circle) should be (1)
    }
    it should "return infinity when calculating a complete graph" in 
    {
      //val complete = new graph()
      //toughness(complete) should be infinity
    }
    it should "return 0.25 with running example 1" in
    {
    }
    it should "return 0.5 with running example 2" in 
    {
    }

  "The toughness finder" should
    "return a list of subgraphs with toughness greater than t" in
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
