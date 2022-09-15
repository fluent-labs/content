import sbt._

object Dependencies {
  val playWsStandaloneVersion: String = "2.1.3"

  lazy val dto = "io.fluentlabs" % "dto" % "1.0.5"

  lazy val opencc4j = "com.github.houbb" % "opencc4j" % "1.7.2"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.8.0"
  lazy val ws =
    "com.typesafe.play" %% "play-ahc-ws-standalone" % playWsStandaloneVersion
  lazy val playJson = "com.typesafe.play" %% "play-json" % "2.9.2"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.12"

  lazy val slf4jApi = "org.slf4j" % "slf4j-api" % "2.0.1"
  lazy val slf4jSimple = "org.slf4j" % "slf4j-simple" % "2.0.1"
}
