package org.eigengo.scalalp.oop

/*
 Define hierarchy

 (1)
   Car (abstract)
   ^ + <init>(make: String)
   | + drive(): Unit (abstract)
   + SportsCar
   | + drive(): Unit
   + RedLorry
     + drive(): Unit

 (2)
 Create OopMain, where you make instances of SportsCar and RedLorry

 (3)
 Add a second constructor to SportsCar and RedLorry that takes no parameters, and calls the
 constructor with name: String, setting it to "No name"

 (4)
 Demonstrate usage in OopMain
 */

