import Dependencies._

val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.4",
  organization := "jp.pigumer"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  aggregate(plist)

lazy val plist = (project in file("./plist")).
  settings(commonSettings: _*).
  settings(
  name := "plist",
  libraryDependencies ++= deps
)

