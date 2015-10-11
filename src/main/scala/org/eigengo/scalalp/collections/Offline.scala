package org.eigengo.scalalp.collections

import scala.io.Source

object Offline extends App {

  lazy val products: List[Product] = {
    val is = Source.fromInputStream(Offline.getClass.getResourceAsStream("/booze.txt"))
    is.getLines().flatMap { line ⇒
      line.split('|') match {
        case Array(name, price, shelfCategoryName) ⇒ Some(Product(name, BigDecimal(price), shelfCategoryName))
        case _ ⇒ None
      }
    }.toList
  }

  def search(query: String): List[Product] =
    products.filter(p ⇒ p.name.contains(query) || p.shelfCategoryName.contains(query))

  def mostExpensive(query: String): Product =
    search(query).maxBy(_.price)

  def cheapest(query: String): Product =
    search(query).minBy(_.price)


  println(mostExpensive("Cider"))

}
