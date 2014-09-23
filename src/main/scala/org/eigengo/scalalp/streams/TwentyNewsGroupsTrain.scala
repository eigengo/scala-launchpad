package org.eigengo.scalalp.streams

import java.io.InputStream
import java.util.zip.ZipEntry
import scala.io.{Codec, Source}

object TwentyNewsGroupsTrain {

  import nak.NakContext._
  import nak.data._
  import nak.liblinear.LiblinearConfig

  import java.io.File

  def processEntry(entry: ZipEntry, contents: InputStream): Option[Example[String, String]] = {
    val names = entry.getName.split('/')
    if (names.length == 3 && !names(2).isEmpty) {
      val text = Source.fromInputStream(contents)(Codec.ISO8859).mkString
      Some(Example(names(1), text, names(2)))
    } else None
  }

  def main(args: Array[String]) {
    def fromLabelled(top: String)(entry: ZipEntry, contents: InputStream): Option[Example[String, String]] = ???
    // Example stopword set (you should use a more extensive list for actual classifiers).
    val stopwords = Set("the", "a", "an", "of", "in", "for", "by", "on")

    // Train
    print("Training... ")
    val zipFile = new ZipArchive(new File(getClass.getResource("/20news-bydate-train.zip").toURI))
    /*
    The zip file contains top-level directory 20news-bydate-train with sub-directories like ``alt.atheism``,
    ``comp.graphics``, ..., ``sci.space`` and such like. Inside each of those directories, there are the newsgroup
    messages, each in its own file. We will use the directory name as the label, the body of the message as the
    features, and the file name as the id.

    So, for

     20news-bydate-train
     +--foo
     |  +--message-1.txt (contents=1)
     |  +--message-2.txt (contents=2)
     +--bar
     |  +--message-x.txt (contents=x)
     +--baz
        +--message-y.txt (contents=y)

    trainingExamples should be
    List(
      Example(foo, 1, message-1.txt), Example(foo, 2, message-2.txt),
      Example(bar, x, message-x.txt),
      Example(baz, y, message-y.txt))

    Hint: use the ZipFile#flatMap, giving it appropriate operation.
     */
    val trainingExamples = zipFile.flatMap(processEntry)
    val config = LiblinearConfig(cost = 5.0, eps = 0.01)
    val featurizer = new BowFeaturizer(stopwords)
    val classifier = trainClassifierHashed(config, featurizer, trainingExamples, 50000)

    saveClassifier(classifier, "20news.classify")
    println("done.")
  }

}
