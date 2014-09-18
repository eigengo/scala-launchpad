package org.eigengo.scalalp.streams

import nak.core.{FeaturizedClassifier, IndexedClassifier}
import scala.util.{Success, Failure}
import akka.stream.{FlowMaterializer, MaterializerSettings}
import akka.stream.scaladsl.Flow
import akka.actor.ActorSystem
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
      val headerIndex = line.indexOf("\\n\\n")
      val header = line.substring(0, headerIndex)
      val headerElements = header.split("\\\\n").flatMap { e =>
        val i = e.indexOf(':')
        if (i != -1 && i + 2 < e.length) Some(e.substring(0, i) -> e.substring(i + 2)) else None
      }.toMap

      val text = line.substring(headerIndex + 3)
      Message(headerElements("From"), headerElements("Subject"), text)
    }
  }

  def main(args: Array[String]) {
    // load the pre-trained classifier
    val classifier = loadClassifierFromResource[IndexedClassifier[String] with FeaturizedClassifier[String, String]]("/20news.classify")

    // prepare the actor system
    implicit val system = ActorSystem("Sys")
    // construct the default materializer
    val materializer = FlowMaterializer(MaterializerSettings())
    // load the source file
    val source = Source.fromURI(getClass.getResource("/20news-test.txt").toURI)(Codec.ISO8859)

    // line: String ->
    //    _.length > 10 ->
    //    Message.parse ->
    //    m => Classified(classifier.predict(m.text), m) ->
    // cm => println(cm.label)

    Flow(source.getLines()).
      filter(_.length > 10).
      map(Message.parse).
      map(message => Classified(classifier.predict(message.text), message)).
      foreach(cm => println(cm.label)).
      onComplete(materializer) {
        case _ => system.shutdown()
      }
    }

}
