organization := "com.github.olafurpg"

version := "0.1.0-SNAPSHOT"

name := "postgres-driver"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.github.tminglei" %% "slick-pg" % "0.11.2",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"
)

bintrayPackageLabels := Seq("postgres", "driver", "slick")

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
