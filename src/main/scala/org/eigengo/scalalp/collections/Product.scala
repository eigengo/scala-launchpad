package org.eigengo.scalalp.collections

/**
 * Models one of the products with its name, price and the shelf category
 * @param name the product name
 * @param price the price
 * @param shelfCategoryName the shelf
 */
case class Product(name: String, price: BigDecimal, shelfCategoryName: String)
