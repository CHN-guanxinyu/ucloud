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
    } else ""))
  )
}
