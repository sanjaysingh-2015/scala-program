name := """movie-store"""
organization := "com.sanjay"

version := "1.0"

scalaVersion := "2.12.14"

libraryDependencies ++= Seq(
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.9.0",
  "org.mongodb" % "mongodb-driver-async" % "3.12.14",
  "org.mongodb" % "bson" % "4.9.0"
)

// Play Framework settings
lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    scalaVersion := "2.12.14",
    libraryDependencies ++= Seq(
      guice,
      "com.typesafe.play" %% "play-guice" % play.core.PlayVersion.current,
      "com.typesafe.play" %% "play-akka-http-server" % play.core.PlayVersion.current,
      "com.typesafe.play" %% "play-slick" % "5.1.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.1.0",
      "com.typesafe.play" %% "play-json" % "2.9.4",
      "org.reactivemongo" %% "play2-reactivemongo" % "1.1.0-play28-RC10",
      "com.typesafe.play" %% "play-test" % play.core.PlayVersion.current % "test",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % "test"
    )
  )
