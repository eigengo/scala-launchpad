package org.eigengo.scalalp.streams

import java.util.zip.{ZipEntry, ZipFile}
import java.io.{InputStream, File}

class ZipArchive(file: File) {
  val zipFile = new ZipFile(file, ZipFile.OPEN_READ)

  /**
   * Apply ``operation`` to every entry in the zip file, collecting the result if the
   * operation is defined, skipping it if it is not defined
   *
   * @param operation the operation to apply to every entry
   * @tparam B the return type of the operation
   * @return all ``x``s for which ``operation`` returns ``Some(x: B)``
   */
  def flatMap[B](operation: (ZipEntry, InputStream) => Option[B]): List[B] = {
    import scala.collection.JavaConversions._
    val entries = zipFile.entries().toList
    entries.flatMap { entry =>
      val is = zipFile.getInputStream(entry)
      val result = operation(entry, is)
      is.close()

      result
    }
  }

}
