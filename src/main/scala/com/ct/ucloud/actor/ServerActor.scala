package com.ct.ucloud.actor
import com.ct.ucloud._
import com.ct.ucloud.rest.WebServer
import io.github.tobetwo.implicits._

/**
  *
  * @param conf
  */
class ServerActor(val conf: UCloudConfig) extends CommonActor(conf) {
  override def receive: Receive = {
    case Register(client) => processRegister(client)
    case HeartBeat(clientId) => processHeartBeat(clientId)
    case GetEnv => sender ! conf
  }

  /**
    *
    */
  WebServer start this
  /**
    *
    */
  private val _clientManager = new ClientManager(debug = false)

  /**
    *
    * @param client
    */
  private def processRegister(client: Client): Unit = {
    log info "register"
    client.ref = sender
    sender ! RegisterResp(_clientManager register client)
  }

  /**
    *
    * @param id
    */
  private def processHeartBeat(id: ClientId) = {
    log info s"recieved heartbeat from $id"
    if(!_clientManager.heartBeat(id))
      sender ! OutOfDateBeat
  }

  def listClients =
     _clientManager.listClients.toJson

  def listDevices(clientId: ClientId) =
    _clientManager.listDevices(clientId).map{ case Device(id, name, desc, typ, _) =>
      Map(
        "id" -> id.id.toString,
        "name" -> name,
        "desc" -> desc,
        "type" -> typ
      )
    }.toJson

  def runJob(clientId: Long, deviceId: Long, args: String*): String = _clientManager.runJob(clientId, deviceId, args: _*)
}