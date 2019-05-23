lazy val ucloud = preownedKittenProject("ucloud", ".")
  .settings(libraryDependencies ++= Lib.akka.all)
  .settings(libraryDependencies += Lib.scala_xml)
  .settings(CommonSetting.projectSettings)



/**
  * 创建通用模板
  */
def preownedKittenProject(
  name : String,
  path : String
) : Project = {
  Project(name, file(path)).
    settings(
      version := "1.0.0-SNAPSHOT",
      organization := "io.github.tobetwo",
      scalaVersion := Version.scala
    )
}