import sbt._

object Dependencies {
  val playWsStandaloneVersion: String = "2.1.3"

  lazy val opencc4j = "com.github.houbb" % "opencc4j" % "1.6.1"
  lazy val cats = "org.typelevel" %% "cats-core" % "2.6.1"
  lazy val ws =
    "com.typesafe.play" %% "play-ahc-ws-standalone" % playWsStandaloneVersion
  lazy val playJson = "com.typesafe.play" %% "play-json" % "2.9.2"
//  lazy val playJson =
//    "com.typesafe.play" %% "play-ws-standalone-json" % playWsStandaloneVersion
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.2.8"
}
