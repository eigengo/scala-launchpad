package org.eigengo.scalalp.oop

/*
 (1)
 Food (interface)
   ^
   +-- Grass (case class)
   +-- Peanut (case class)
   +-- Animal (interface)
   |     + eat(food: Food): Unit
   +-- Cow (case class)
   +-- Shark (case class)
   +-- Parrot (case class)


 (2)
 Write a main class, constructing
   milka: Cow
   budgie: Parrot
   sharky: Shark

 observe that
   sharky.eat(milka)
   sharky.eat(parrot)

 but also that
   milka.eat(sharky)
   parrot.eat(milka)

 :(

 (3)
 Modify Animal#eat to take some abstract type SuitableFood, subtype of Food, which has to be
 defined in the subclasses. Make sure that you implement the SuitableFood appropriately so that

   milka.eat(sharky)

 and similar do not compile
 */

class Food
class Grass extends Food
class Peanuts extends Food

case class Age(months: Int) extends AnyVal {
  def addYears(years: Int): Age = Age(months + years * 12)
}
case class Volume(value: Int) extends AnyVal

trait Speaking {
  def speak(message: String): Unit = println(s"I say $message")
}
trait WithLisp extends Speaking {
  override def speak(message: String): Unit = super.speak("Lisping this " + message)
}
trait Loudly extends Speaking {
  var volume: Volume = Volume(400)
  override def speak(message: String): Unit = super.speak(message.toUpperCase)
}

class Animal extends Food {
  type SuitableFood <: Food
  def eat(f: SuitableFood): Unit = println(s"$this: Yum yum $f")
}
case class Parrot(name: String, age: Age, numberOfFeathers: Int) extends Animal {
  type SuitableFood = Peanuts
}
case class Cow(name: String) extends Animal {
  type SuitableFood = Grass
}
final case class Shark(name: String) extends Animal {
  type SuitableFood = Food

  def swim(): Unit = ()
}

object Zoo extends App {
  val milka  = Cow("Milka")
  val milka2 = Cow("Milka")
  val dizzy  = Cow("Dizzy")
  val sharky = Shark("Sharky")

  val jansParrot = Parrot("Jan's parrot", Age(4).addYears(12), 40000)
  val pirateParrot = new Parrot("Yarrrgh!", Age(20), 40000) with Loudly with WithLisp
  val pirateParrot2 = new Parrot("Yarrrgh!", Age(20), 40000) with Loudly with WithLisp
  pirateParrot.speak("sfsdf")
  println(pirateParrot == pirateParrot2)

//  milka.eat(sharky)
//  dizzy.eat(dizzy)
  sharky.swim()
  sharky.eat(milka)
}
