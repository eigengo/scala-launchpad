package org.eigengo.scalalp.collections

import scala.io.Source

object Offline extends App {

  lazy val byCategory: Map[String, List[Product]] = products.groupBy(_.shelfCategoryName)

  lazy val products: List[Product] = {
    val is = Source.fromInputStream(Offline.getClass.getResourceAsStream("/booze.txt"))
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

