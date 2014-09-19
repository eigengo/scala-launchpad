package org.eigengo.scalalp.intro

object HelloWorld extends App {

  // Hello, world
  println("Hello, world")

  // Everything is expression
  val msg = if (System.currentTimeMillis() % 2 == 0) "even" else "odd"
  println(s"Message is $msg")

}
