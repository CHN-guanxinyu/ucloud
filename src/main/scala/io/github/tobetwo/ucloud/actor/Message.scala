package io.github.tobetwo.ucloud.actor

import io.github.tobetwo.ucloud.{Client, ClientId, JobId}

case object GetEnv

case class Register(client: Client) extends Serializable

case class RegisterResp(clientId: ClientId) extends Serializable

case class HeartBeat(clientId: ClientId) extends Serializable

case object OutOfDateBeat extends Serializable

case object Close extends Serializable

case object ListClients extends Serializable

case class ListJobs(clientId: ClientId) extends Serializable

case class RunJob(jobId: JobId, args: String*) extends Serializable