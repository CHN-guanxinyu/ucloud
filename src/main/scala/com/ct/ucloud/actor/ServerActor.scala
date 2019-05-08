package com.ct.ucloud.actor
import com.ct.ucloud._
import com.ct.ucloud.rest.WebServer
import com.keene.core.implicits._
class ServerActor(val conf: UCloudConfig) extends CommonActor(conf) {
  override def receive: Receive = {
    case Register(client) => processRegister(client)
    case HeartBeat(clientId) => processHeartBeat(clientId)
    case GetEnv => sender ! conf
  }
  WebServer.start(this)

  private val _clientManager = new ClientManager(debug = true)

  private def processRegister(client: Client): Unit = {
    log info "register"
    client.ref = sender
    sender ! RegisterResp(_clientManager register client)
  }

  private def processHeartBeat(id: ClientId) = {
    log info s"recieved heartbeat from $id"
    if(!_clientManager.heartBeat(id))
      sender ! OutOfDateBeat
  }

  def listClients =
     _clientManager.listClients.toJson

  def listDevices(clientId: ClientId) =
    _clientManager.listDevices(clientId).map{ case Device(id, name, _, _) =>
      Map("id" -> id.id.toString, "name" -> name)
    }.toJson

  def runJob(clientId: Long, deviceId: Long, args: String*) = _clientManager.runJob(clientId, deviceId, args: _*)
}