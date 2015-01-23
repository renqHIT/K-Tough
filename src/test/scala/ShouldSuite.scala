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
      //toughness(line) should be 0.5
    }
    it should "return 1 when calculating a circle graph" in
    {
      //val circle = new graph()
      //toughness(circle) should be 1
    }
}
