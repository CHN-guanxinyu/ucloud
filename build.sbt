name := "ucloud"

version := "1.0.1-SNAPSHOT"

organization := "io.github.tobetwo"

scalaVersion := "2.11.8"

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) {
    Some("snapshot" at nexus + "content/repositories/snapshots")
  } else {
    Some("releases" at nexus + "service/local/staging/deploy/maven2/")
  }
}

// 移除生成构件的 scala version
crossPaths := false

publishArtifact in Test := false

pomIncludeRepository := { _ ⇒
  false
}

pomExtra in Global := {
  <url>https://github.com/tobetwo/scala.zoom</url>
    <licenses>
      <license>
        <name>MIT</name>
        <url>https://opensource.org/licenses/MIT</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:tobetwo/scala.zoom.git</url>
      <connection>scm:git:git@github.com:tobetwo/scala.zoom.git</connection>
    </scm>
    <developers>
      <developer>
        <id>tobetwo</id>
        <name>Afflatus</name>
        <url>http://tobetwo.github.io/</url>
      </developer>
    </developers>
}

libraryDependencies ++= Dependencies.core
libraryDependencies ++= Lib.akka.all
libraryDependencies += Lib.scala_xml
