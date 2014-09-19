package org.eigengo.scalalp.oop

abstract class Car(make: String) {
  def drive(): Unit
}

class SportsCar(make: String) extends Car(make) {
  def this() = this("No name")

  override def drive(): Unit = println(s"Vroom! $make")
}

class RedLorry(make: String) extends Car(make) {
  def this() = this("No name")

  override def drive(): Unit = println("https://www.youtube.com/watch?v=8iusUq4-f5U")
}

object OopMain extends App {
  val c1 = new SportsCar("Ferrari")
  val c2 = new RedLorry()

  c1.drive()
  c2.drive()
}