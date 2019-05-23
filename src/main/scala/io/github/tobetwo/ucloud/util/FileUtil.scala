package io.github.tobetwo.ucloud.util

import java.io.{File, FileInputStream}
import java.nio.file.Paths

import akka.http.scaladsl.model.HttpEntity.limitableByteSource
import akka.stream.scaladsl.FileIO
import javax.print.attribute.{HashDocAttributeSet, HashPrintRequestAttributeSet}
import javax.print.{DocFlavor, PrintServiceLookup, SimpleDoc}

object FileUtil {
  def stream(filePath: String, chunkSize: Int = 256) =
    limitableByteSource(FileIO.fromPath(Paths get filePath, chunkSize))

  def print(filePath: String): String = print(new File(filePath))

  def print(file: File): String = {
    val pras = new HashPrintRequestAttributeSet
    val flavor = DocFlavor.INPUT_STREAM.AUTOSENSE
    val services = PrintServiceLookup lookupPrintServices(flavor, pras)
    val defaultService = PrintServiceLookup.lookupDefaultPrintService
    if (defaultService != null) {
      try {
        val job = defaultService.createPrintJob
        val fis = new FileInputStream(file)
        val das = new HashDocAttributeSet
        val doc = new SimpleDoc(fis, flavor, das)
        job.print(doc, pras)
        "success"
      } catch {
        case e: Throwable => e.printStackTrace
          s"error: $e"
      }
    } else "cannot find a default print service."
  }
}
