package com.ct.ucloud

import io.github.tobetwo.implicits._

import scala.util.Random
import scala.xml.{NodeSeq, XML}
case class DeviceId(id: Long = Random.nextInt(Integer.MAX_VALUE)) extends AnyVal
case class Device(
  id: DeviceId,
  name: String,
  desc: String,
  typ: String,
  driver: String
) extends Serializable
class DeviceManager(configPath: String) {
  private implicit def node2Text(node : NodeSeq) = node.text
  private val _devices = XML.loadFile(configPath) \ "device" map{ device =>
    val id = new DeviceId
    id -> Device(id, device \ "name", device \ "desc",device \ "type", device \ "driver")
  } toMap

  def devices = _devices.values.toList

  def runJob(id: DeviceId, args: String*) = try{
    _devices(id).driver.as[DeviceDriver].runJob(args: _*)
  } catch {
    case e => e.printStackTrace
      e.toString
  }
}
