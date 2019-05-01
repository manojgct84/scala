package scala.com.scala.project


import scala.collection.mutable._


object ScalaCollection {

  def main(args: Array[String]): Unit = {
    var list: ListBuffer[String] = ListBuffer();
    var set: SortedSet[String] = SortedSet();
    var map: Map[String, String] = Map();

    list += "Manoj"
    list += "Kuamr"

    set += "Manoj"
    set += "kumar"


    map += ("k2" -> "v2", "k3" -> "v3")


    list.foreach(println)
    map.foreach(println)
    set.foreach(println)

     var emptyLst: ListBuffer[Nothing] =  ListBuffer()

    print(emptyLst.isEmpty);



  }
}
