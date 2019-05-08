package com.ct.ucloud

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.ct.ucloud.actor.{Close, CommonActor}
import com.keene.core.implicits._
import com.keene.core.parsers.Arguments
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object Main extends App {

  val mainArgs = args.as[MainArgs]
  require(mainArgs.configFile.nonEmpty, "--config-file must be a valid file path")
  require(mainArgs.action.nonEmpty, "--action must be specific")
  require(mainArgs.mode.nonEmpty, "--mode must be specific")

  val ucloudConfig = ConfigurationHelper load mainArgs.configFile
  val opt = ConfigFactory.parseString(
    s"""
       |akka.remote.netty.tcp.hostname=${ucloudConfig.UCLOUD_HOST}
       |akka.remote.netty.tcp.port=${if (mainArgs.action == "start") ucloudConfig.UCLOUD_PORT else 0}
    """.stripMargin
  ).withFallback(ConfigFactory.load)

  val system = ActorSystem("UCloud", opt)

  implicit val ec = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  mainArgs.action match {
    case "start" =>
      system.actorOf(Props(s"com.ct.ucloud.actor.${mainArgs.mode.headToUpper}Actor".as[CommonActor](ucloudConfig)), mainArgs.mode)
    case "stop" =>
      system.actorSelection(s"akka.tcp://UCloud@${ucloudConfig.UCLOUD_HOST}:${ucloudConfig.UCLOUD_PORT}/user/${mainArgs.mode}") ? Close onComplete
        (_ => system.terminate)
  }
}

class MainArgs(
  var configFile: String = "",
  var mode: String = "",
  var action: String = "",
  var clientPort: Int = 0
) extends Arguments {
  override def usage: String =
    """
      |--config-file
      |--mode server|client
      |--action start|stop
      |--client-port
    """.stripMargin
}
