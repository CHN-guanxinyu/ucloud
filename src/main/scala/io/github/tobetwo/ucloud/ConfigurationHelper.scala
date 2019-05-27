package io.github.tobetwo.ucloud

import java.io.InputStream

import org.apache.commons.configuration.PropertiesConfiguration

import scala.collection.JavaConversions._

object ConfigurationHelper {
  def load(stream: InputStream) = {
    val config = new PropertiesConfiguration
    config.load(stream)
    new UCloudConfig(config)
  }

  def load(path: String) = {
    val config = new PropertiesConfiguration
    config setEncoding "UTF-8"
    config load path
    new UCloudConfig(config)
  }
}

class UCloudConfig(@transient config: PropertiesConfiguration) extends Serializable {

  private val server = "ucloud.server" **
  private val local = "ucloud.local" **
  private val rest = "rest" **
  private val fc = "file.client" **

  val UCLOUD_HOST = local ~ ("host", "localhost")
  val UCLOUD_PORT = local ~ ("port", "0")
  val UCLOUD_NAME = local ~ "name"
  val JOB_CONFIG_FILE = local ~ ("job.config", "")

  val UCLOUD_SERVER_HOST = server ~ "host"
  val UCLOUD_SERVER_PORT = server ~ "port"

  val REST_HOST = rest ~ "host"
  val REST_PORT = rest ~ "port"

  val LOCAL_DIR = "local.dir".prop

  private implicit class Config(s: String) {
    def prop = config getString s

    def ** = config getKeys s map (e => e.substring(s.length + 1) -> e.prop) toMap
  }

  private implicit class Mapper(map: Map[String, String]) {
    def ~(k: String, d: String = "") = map.getOrElse(k, d)
  }


}