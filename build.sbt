import Dependencies._

val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.11.8",
  organization := "jp.pigumer"
)

lazy val plist = (project in file("./plist")).
  settings(commonSettings: _*).
  settings(
  name := "plist",
  libraryDependencies ++= deps
)
