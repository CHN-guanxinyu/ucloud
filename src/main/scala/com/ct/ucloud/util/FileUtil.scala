package com.ct.ucloud.util

import java.nio.file.Paths

import akka.http.scaladsl.model.HttpEntity.limitableByteSource
import akka.stream.scaladsl.FileIO

object FileUtil {
  def stream(filePath: String, chunkSize: Int = 256) =
    limitableByteSource(FileIO.fromPath(Paths get filePath, chunkSize))
}
