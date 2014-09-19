package org.eigengo.scalalp.oop

trait Food
case class Grass() extends Food
case class Peanut() extends Food

trait Animal extends Food {
  type SuitableFood <: Food
  def eat(food: SuitableFood): Unit = println(s"Munching $food")
}

class Cow extends Animal {
  type SuitableFood = Grass
}

class Shark extends Animal {
  type SuitableFood = Food
}

class Parrot extends Animal {
  type SuitableFood = Grass
}

object ZooMain extends App {
  val milka = new Cow
  val milka2: Animal = new Cow
  val budgie = new Parrot
  val sharky = new Shark

  sharky.eat(milka)
  sharky.eat(budgie)
  sharky.eat(sharky)

  // milka.eat(budgie)
  // budgie.eat(sharky)
  // milka2.eat(sharky)
}
