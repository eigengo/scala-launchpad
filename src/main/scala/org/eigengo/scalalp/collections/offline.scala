package org.eigengo.scalalp.collections

import scala.io.Source

object Offline extends App {

  lazy val byCategory: Map[String, List[Product]] = ???

  lazy val products: List[Product] = {
    val is = Source.fromInputStream(Offline.getClass.getResourceAsStream("/booze.txt"))
    // Use ``is.getLines()`` to get ``Iterator[String]`` representing each line.
    // Convert each line to a ``Product`` (the format is ``name '|' price '|' category``), where
    // ``name`` and ``category`` are ``String``s, and ``price`` is ``BigDecimal``.
    // Consume all lines by converting the result to ``List[Product]``.
    //
    // N.B. some lines are malformed and do not contain three elements (oopsie! :))
    is.getLines().flatMap { line =>
      val arr = line.split('|')

      if (arr.length != 3) None else Some(Product(arr(0), BigDecimal(arr(1)), arr(2)))
    }.toList
  }

  def search(query: String): List[Product] =
    products.filter(p => p.name.contains(query) || p.shelfCategoryName.contains(query))

  def mostExpensive(query: String): Product =
    search(query).maxBy(_.price)

  def cheapest(query: String): Product =
    search(query).minBy(_.price)


  println(mostExpensive("Cider"))

}

