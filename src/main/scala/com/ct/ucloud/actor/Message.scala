package com.ct.ucloud.actor

import com.ct.ucloud.{Client, ClientId, DeviceId}

case object GetEnv
case class Register(client: Client) extends Serializable
case class RegisterResp(clientId: ClientId) extends Serializable
case class HeartBeat(clientId: ClientId) extends Serializable
case object OutOfDateBeat extends Serializable
case object Close extends Serializable

case object ListClients extends Serializable

case class ListDevices(clientId: ClientId) extends Serializable

case class RunJob(deviceId: DeviceId, args: String*) extends Serializable