package io.github.tobetwo.ucloud.actor

import akka.actor.{Actor, ActorLogging}
import akka.util.Timeout

import scala.concurrent.duration._

trait ActorEnv extends Actor with ActorLogging {
  protected implicit lazy val system = context.system
  protected implicit lazy val ec = system.dispatcher
  protected implicit lazy val scheduler = system.scheduler
  protected implicit lazy val timeout = Timeout(10 seconds)
}
