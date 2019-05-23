package com.ct.ucloud

import io.github.tobetwo.implicits._

import scala.util.Random
import scala.xml.{NodeSeq, XML}
case class JobId(id: Long = Random.nextInt(Integer.MAX_VALUE)) extends AnyVal
case class Job(
  id: JobId,
  name: String,
  desc: String,
  typ: String,
  driver: String
) extends Serializable
class JobManager(configPath: String) {
  private implicit def node2Text(node : NodeSeq) = node.text
  private val _jobs = XML.loadFile(configPath) \ "job" map{ job =>
    val id = new JobId()
    id -> Job(id, job \ "name", job \ "desc", job \ "type", job \ "driver")
  } toMap

  def devices = _jobs.values.toList

  def runJob(id: JobId, args: String*) = try{
    _jobs(id).driver.as[JobDriver].runJob(args: _*)
  } catch {
    case e => e.printStackTrace
      e.toString
  }
}
