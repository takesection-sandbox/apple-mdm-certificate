import Dependencies._

val commonSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.4",
  organization := "com.pigumer.mdm",
  name := "darwin-certificate"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= deps
  )
