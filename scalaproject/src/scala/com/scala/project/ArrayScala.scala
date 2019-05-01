package scala.com.scala.project

import Array._

object ArrayScala {
  def main(args: Array[String]): Unit = {
    arrayFunction(10)
  }

  def arrayFunction(size: Int): Unit = {

    val array = new Array[Int](size)

    for (i <- array) {
      array(i) = i
    }
    for (i <- array) {
      print("Array", array(i))
    }

    var twoDimArray = ofDim[Int](1, 2)

  }
}
