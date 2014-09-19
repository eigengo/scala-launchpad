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

