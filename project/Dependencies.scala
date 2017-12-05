import sbt._

object Dependencies {

  // https://mvnrepository.com/artifact/org.bouncycastle/bcpkix-jdk15on
  val bcpkix = "org.bouncycastle" % "bcpkix-jdk15on" % "1.58"
  // https://mvnrepository.com/artifact/org.bouncycastle/bcmail-jdk15on
  val bcmail = "org.bouncycastle" % "bcmail-jdk15on" % "1.58"
  // https://mvnrepository.com/artifact/org.bouncycastle/bcpg-jdk15on
  val bcpg   = "org.bouncycastle" % "bcpg-jdk15on" % "1.58"

  // https://mvnrepository.com/artifact/org.specs2/specs2_2.11
  val specs2 = "org.specs2" % "specs2_2.11" % "3.7" % Test

  lazy val deps = Seq(
    bcpkix,
    bcmail,
    bcpg,
    specs2
  )
}