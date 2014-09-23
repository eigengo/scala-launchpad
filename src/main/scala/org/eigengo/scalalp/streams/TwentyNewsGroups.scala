package org.eigengo.scalalp.streams

import akka.actor.ActorSystem
import akka.stream.scaladsl.Flow
import akka.stream.{FlowMaterializer, MaterializerSettings}
import nak.core.{FeaturizedClassifier, IndexedClassifier}

import scala.io.{Codec, Source}

object TwentyNewsGroups {

  import nak.NakContext._

  /**
   * Holds the label with the classified value
   * @param label the computed label
   * @param value the value
   * @tparam L type of L
   * @tparam V type of V
   */
  case class Classified[L, V](label: L, value: V)

  /**
   * A newsgroup message with just the ``header``, ``subject`` headers, and its ``text``
   * @param from the from header
   * @param subject the subject header
   * @param text the body
   */
  case class Message(from: String, subject: String, text: String)

  /**
   * Companion object for the ``Message`` case class. It contains the method ``parse``, which parses a String and
   * returns a ``Message``.
   *
   * It is usual pattern to have convenience methods in the companion object. In our case, the [[Message#parse]] method
   * does just that.
   */
  object Message {
    /**
     * Parse a message in format
     *
     * ```
     * From: decay@cbnewsj.cb.att.com (dean.kaflowitz)
     * Subject: Re: about the bible quiz answers
     * Organization: AT&T
     * Distribution: na
     * Lines: 18
     *
     * In article <healta.153.735242337@saturn.wwc.edu>, ...
     * ```
     * @param line the line with the headers on each line, with body separated by two \n\n
     * @return the parsed message
     */
    def parse(line: String): Message = {
      // The headers are separated from the body by ``\n\n`` (the character backslash and n), not the \n control
      // character.
      val headerIndex = line.indexOf("\\n\\n")
      val header = line.substring(0, headerIndex)
      // We're splitting the string on the ``\n`` characters. ``\n`` does not mean the new line character.
      // Now, since the parameter of String#split is a regular expression, it must be ``\\n``.
      // Therefore, we have four backslashes in the String literal.
      val headerElements = header.split("\\\\n").flatMap { e =>
        // e is for example ``Subject: Foobarbaz``
        val i = e.indexOf(':')
        // check whether we found ``:``, and whether the following String has any content
        // Notice that we return tuple (header-name, header-value)
        if (i != -1 && i + 2 < e.length) Some(e.substring(0, i) -> e.substring(i + 2)) else None
      }.toMap   // convert the list of tuples (k, v) to a map

      val text = line.substring(headerIndex + 3)
      Message(headerElements("From"), headerElements("Subject"), text)
    }
  }

  /**
   * Main entyr point
   * @param args the args
   */
  def main(args: Array[String]) {
    // load the pre-trained classifier
    val classifier = loadClassifierFromResource[IndexedClassifier[String] with FeaturizedClassifier[String, String]]("/20news.classify")

    // prepare the actor system
    implicit val system = ActorSystem("Sys")
    // construct the default materializer
    implicit val materializer = FlowMaterializer(MaterializerSettings(system))
    // load the source file
    val source = Source.fromURI(getClass.getResource("/20news-test.txt").toURI)(Codec.ISO8859)

    // this brings into scope the ``implicit ec: ExecutionContext``, which is required for ``onComplete``
    import system.dispatcher

    Flow(source.getLines()).    // construct a flow by consuming the source lines and then:
      filter(_.length > 10).    // filtering for lines that are at least 10 characters long
      map(Message.parse).       // parsing each filtered line to turn it into a Message
      map(m => Classified(classifier.predict(m.text), m)).  // predicting the message's topic using the trained classifier
      foreach(println).         // displaying the output
      // At this moment, we have a flow that we want, but it is not executing yet.
      // To kick off the execution, we call one of the terminating combinators: in this case, onComplete.
      onComplete {
        case _ => system.shutdown() // when the execution completes, we shutdown the ActorSystem
      }


    // Compare the block above with the same flow on plain collections:

//    source.getLines()
//      .filter(_.length > 10)
//      .map(Message.parse)
//      .map(m => Classified(classifier.predict(m.text), m))
//      .foreach(println)
    }

}
