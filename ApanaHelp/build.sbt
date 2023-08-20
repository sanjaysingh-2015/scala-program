
ThisBuild / scalaVersion := "2.13.11"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """ApanaHelp""",
    libraryDependencies ++= Seq(
      guice,
      "com.google.inject" % "guice" % "5.1.0",
      "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0",
      "com.typesafe.play" %% "play-slick" % "5.1.0",
      "com.typesafe.play" %% "play-slick-evolutions" % "5.1.0",
      "org.postgresql" % "postgresql" % "42.5.4",
      "ch.qos.logback" % "logback-classic" % "1.4.7",
      "com.opencsv" % "opencsv" % "5.5.2",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "org.mockito" %% "mockito-scala" % "1.17.7" % Test
    )
  )