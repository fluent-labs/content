import Dependencies._
import sbt._

ThisBuild / scalaVersion := "2.13.9"
ThisBuild / organization := "io.fluentlabs"
ThisBuild / organizationName := "FluentLabs"

lazy val settings = Seq(
  scalacOptions ++= compilerOptions,
  githubTokenSource := TokenSource.Or(
    TokenSource.Environment("GITHUB_TOKEN"),
    TokenSource.GitConfig("github.token")
  ),
  releaseVersionBump := sbtrelease.Version.Bump.Bugfix,
  publishConfiguration := publishConfiguration.value.withOverwrite(true),
  publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(
    true
  ),
  organization := "io.fluentlabs",
  githubOwner := "fluent-labs",
  githubRepository := "content"
)

lazy val root = (project in file("."))
  .settings(
    name := "content",
    settings,
    libraryDependencies ++= Seq(
      dto,
      cats,
      ws,
      playJson,
      opencc4j,
      slf4jApi,
      slf4jSimple,
      scalaTest % Test
    )
  )

lazy val compilerOptions = Seq(
  "-encoding",
  "utf8",
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Xfatal-warnings",
  "-Wdead-code",
  "-Wvalue-discard"
)

// Code coverage settings
coverageMinimumStmtTotal := 70
coverageFailOnMinimum := false

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
