name := """user-demo"""
organization := "com.techsophy"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.11"

libraryDependencies += guice
libraryDependencies += "com.datastax.oss" % "java-driver-core" % "4.15.0"
libraryDependencies += "com.datastax.oss" % "java-driver-query-builder" % "4.15.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC8"
libraryDependencies += "com.typesafe.play" %% "play-ws" % "2.9.0-M5"
libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "1.0.2"
libraryDependencies +=  "ch.qos.logback" % "logback-classic" % "1.4.7"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.techsophy.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.techsophy.binders._"
