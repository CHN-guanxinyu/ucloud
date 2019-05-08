package com.ct.ucloud

import java.io.File
import java.nio.file.Paths

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.FileIO
import com.ct.ucloud.util.FileUtil

object FileManager {
  implicit lazy val sys = ActorSystem("UCloud")
  implicit lazy val mat = ActorMaterializer()
  implicit lazy val ec = sys.dispatcher

  private var _host: String = _
  private var _port: Int = _
  private var _localDir: String = _

  def init(host: String, port: Int, localDir: String) = {
    _host = host
    _port = port
    _localDir = localDir
  }

  private lazy val fileApi = s"http://${_host}:${_port}/file"

  def download(path: String, temp: Boolean = true)(f: File => Unit) = Http(sys) singleRequest
    HttpRequest(uri = s"$fileApi/$path") onSuccess {
      case HttpResponse(StatusCodes.OK, _, entity, _) =>
        val localPath = _localDir + "/" + path
        entity.dataBytes.runWith(FileIO toPath Paths.get(localPath)).onSuccess { case _ =>
          val file = new File(localPath)
          f(file)
          if (temp) file.delete
        }
      case HttpResponse(StatusCodes.NotFound, _, _, _) =>
        f(null)
    }

  def upload(path: String) = Http(sys) singleRequest HttpRequest(
    HttpMethods.POST,
    uri = s"$fileApi/$path"
  ).copy(entity = HttpEntity(
    ContentTypes.`application/octet-stream`,
    FileUtil.stream(path)
  ))
}
