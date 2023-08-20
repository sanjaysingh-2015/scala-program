ThisBuild / scalaVersion := "2.13.6"

ThisBuild / version := "1.0-SNAPSHOT"

resolvers += Resolver.jcenterRepo

resolvers += "Sonatype snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

val playSilhouetteVersion = "7.0.0"
val slickVersion = "3.4.1"
val playSlickVersion = "5.1.0"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """NewDemo""",
    libraryDependencies ++= Seq(
      "com.mohiva" %% "play-silhouette" % playSilhouetteVersion,
      "com.mohiva" %% "play-silhouette-password-bcrypt" % playSilhouetteVersion,
      "com.mohiva" %% "play-silhouette-persistence" % playSilhouetteVersion,
      "com.mohiva" %% "play-silhouette-crypto-jca" % playSilhouetteVersion,
      "net.codingwell" %% "scala-guice" % "5.1.1",
      "com.typesafe.slick" %% "slick" % slickVersion,
      "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
      "com.typesafe.play" %% "play-slick" % playSlickVersion,
      "com.typesafe.play" %% "play-slick-evolutions" % playSlickVersion,
      "org.postgresql" % "postgresql" % "42.5.4",
      guice,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
    )
  )