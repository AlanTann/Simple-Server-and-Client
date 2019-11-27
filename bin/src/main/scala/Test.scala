

/**
 * @author User
 */
object Test extends App {
  
  def add(a : Int, b: Int): Int = {
    a + b
  }
  def add2(a: Int)(b: Int): Int = {
    a + b
  }
  def addOther(a:Int):(Int)=>Int ={
    return (b:Int) => a + b
  }
  
  val add4 = addOther(4)
  
  val add3: Int=>Int = add2(3) _
  
  println(add4(2))
}