import sbt.Keys.{onLoadMessage, _}
import sbt._
import sbt.plugins.JvmPlugin

object CommonSetting extends AutoPlugin {
  override def requires = JvmPlugin

  override def trigger = allRequirements

  override def projectSettings: Seq[Def.Setting[_]] = Seq(
    parallelExecution in Test := false,

    fork in Test := true,
    fork in run := true,

    libraryDependencies ++= Dependencies.core,

    onLoadMessage ~= (_ + (if ((sys props "java.specification.version") < "1.8") {
      s"""
         |You seem to not be running Java 1.8.
         |While the provided code may still work, we recommend that you
         |upgrade your version of Java.
    """.stripMargin
    } else "")),

    publishMavenStyle := true,

    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value) {
        Some("snapshot" at nexus + "content/repositories/snapshots")
      } else {
        Some("releases" at nexus + "service/local/staging/deploy/maven2/")
      }
    },

    // 移除生成构件的 scala version
    crossPaths := false,

    publishArtifact in Test := false,

    pomIncludeRepository := { _ => false },

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

  )
}
