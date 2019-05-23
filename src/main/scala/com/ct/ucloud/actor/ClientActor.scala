package com.ct.ucloud.actor

import com.ct.ucloud._

import scala.concurrent.duration._

/**
  *
  * @param conf
  */
class ClientActor(conf: UCloudConfig) extends CommonActor(conf) {

  override def receive: Receive = {
    case env: UCloudConfig =>
      FileManager init(env.REST_HOST, env.REST_PORT.toInt, conf.LOCAL_DIR)
      register
    case RegisterResp(id) => processRegisterResp(id)
    case OutOfDateBeat => register

    case job: RunJob => runJob(job.deviceId, job.args: _*)
  }

  override def preStart() = server ! GetEnv

  /**
    *
    */
  private val _deviceManager = new DeviceManager(conf.DEVICE_CONFIG_FILE)
  /**
    *
    */
  private val server = context actorSelection
    s"akka.tcp://UCloud@${conf.UCLOUD_SERVER_HOST}:${conf.UCLOUD_SERVER_PORT}/user/server"

  private var _myid : ClientId = null
  private def processRegisterResp(myid: ClientId) = {
    log info s"register success, myid = $myid"
    //保证只进行一次定时调度，之后的注册都是过期重新注册，不需要再运行调度
    if(_myid == null)
      context.system.scheduler.schedule(2 seconds, 2 seconds)(server ! HeartBeat(myid))
    _myid = myid
  }
  private def register = server ! Register(Client(conf.UCLOUD_NAME, _deviceManager.devices))

  private def runJob(deviceId: DeviceId, args: String*) =
    sender ! _deviceManager.runJob(deviceId, args: _*)

}
