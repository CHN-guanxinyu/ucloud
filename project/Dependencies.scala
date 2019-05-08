import sbt._

object Dependencies {
  val core = Seq(Lib.commons, Lib.json4s)
}

object Version{
  val akka = "2.5.11"
  val akka_http = "10.0.11"
  val akka_cors = "0.2.2"
  val commons = "1.10"
  //scala
  val scala = "2.11.8"
  val scala_xml = "1.2.0"
  val json4s = "3.6.5"

}

object Lib{
  object akka{
    val http            = "com.typesafe.akka"           %% "akka-http"                  % Version.akka_http
    val http_spray_json = "com.typesafe.akka"           %% "akka-http-spray-json"       % Version.akka_http
    val http_xml        = "com.typesafe.akka"           %% "akka-http-xml"              % Version.akka_http
    val stream          = "com.typesafe.akka"           %% "akka-stream"                % Version.akka
    val remote          = "com.typesafe.akka"           %% "akka-remote"                % Version.akka
    val cluster         = "com.typesafe.akka"           %% "akka-cluster"               % Version.akka
    val cors            = "ch.megard"                   %% "akka-http-cors"             % Version.akka_cors
    val all = Seq(http, remote, cors)
  }
  val scala_xml         = "org.scala-lang.modules"      %% "scala-xml"                  % Version.scala_xml
  val json4s            = "org.json4s"                  %% "json4s-native"              % Version.json4s
  val commons           = "commons-configuration"       % "commons-configuration"       % Version.commons
}
