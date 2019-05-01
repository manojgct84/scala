package scala.learning

object Test {

   def main(args: Array[String]): Unit = {

    println("Ädd two values", add(10, 20))
  }

  private def add(x: Int, y: Int): Any = {
    val sum = x + y;
    return sum
  }
}
