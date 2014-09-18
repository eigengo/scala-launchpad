#Strongly-typed FP workshop
Jan Machacek (<janm@cakesolutions.net>, @honzam399) will show how you can make the most of today's strongly-typed FP 
languages. On the JVM, the workshop includes Scala; outside the JVM, we have Haskell.

If you are new to FP, Scala's functional, strongly-typed and object-oriented nature provides shallower learning 
curve; once you are comfortable with some of the advanced concepts in Scala, you will find the journey into 
Haskell a lot easier.

###Setup on proper OSs
Come to the workshop ready with your computer running sbt and the [Haskell Platform](http://haskell.org). 

* For Sbt, I recommend using [sbt-extras](https://github.com/paulp/sbt-extras). Download the shell script and check that you can run ``sbt``.
* For the Haskell Platform, download & install the package for your OS & matching C++ compiler chain. (For example, on OS X 10.9 you should use the 64bit GHC, and have Xcode 5.)

Verify that both Sbt and GHC work:

```bash
~/$ ghci
GHCi, version 7.6.3: http://www.haskell.org/ghc/  :? for help
Loading package ghc-prim ... linking ... done.
Loading package integer-gmp ... linking ... done.
Loading package base ... linking ... done.
Prelude>
``` 

```bash
~/sandbox/scala-launchpad$ sbt
[info] Set current project to scala-launchpad (in build file:/.../)
>
```

We will be using postfix operators, so you may want to enable them in the ``build.sbt``:

```scala
initialCommands in console := "import language.postfixOps"
```

If all worked as expected, you should be able to run ``sbt console``:

```bash
~/sandbox/scala-launchpad$ sbt
[info] Set current project to scala-launchpad (in build file:/.../)
[info] Starting scala interpreter...
[info]
import language.postfixOps
Welcome to Scala version 2.11.2 (Java HotSpot(TM) 64-Bit Server VM, Java 1.7.0_55).
Type in expressions to have them evaluated.
Type :help for more information.

scala>
```

You are now seeing Scala's REPL, where we will be typing some Scala pixie-dust.

###Setup on Windows
Windows setup is rather clunky, especially if you're not using Cygwin. Follow 
[http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html](http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html) 
for instructions. You need to get to the same position as your Linux / UNIX friends: 
running ``sbt`` on the command prompt works; running ``sbt console`` in a directory with a 
valid ``build.sbt`` file shows the Scala REPL prompt.
