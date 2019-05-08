package com.ct.ucloud

import com.keene.core.implicits._

import scala.util.Random
import scala.xml.{NodeSeq, XML}
case class DeviceId(id: Long = Random.nextInt(Integer.MAX_VALUE)) extends AnyVal
case class Device(
  id: DeviceId,
  name: String,
  desc: String,
  driver: String
) extends Serializable
class DeviceManager(configPath: String) {
  private implicit def node2Text(node : NodeSeq) = node.text
  private val _devices = XML.loadFile(configPath) \ "device" map{ device =>
    val id = DeviceId()
    id -> Device(id, device \ "name", device \ "desc", device \ "driver")
  } toMap

  def devices = _devices.values.toList
  def runJob(id: DeviceId, args: String*) = {
    _devices(id).driver.as[DeviceDriver].runJob(args: _*)
  }
}
