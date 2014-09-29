import com.typesafe.sbt.SbtStartScript

seq(SbtStartScript.startScriptForClassesSettings: _*)

name := "Doc Search"

version := "1.0"

scalaVersion  := "2.10.4"

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8")

resolvers ++= Seq(
	"Typesafe Snapshots" at "http://repo.typesafe.com/typesafe/snapshots/",
	"Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
)


libraryDependencies ++= {
  Seq(
    "org.mongodb" %% "casbah" % "2.7.0",
    "com.typesafe" % "config" % "1.2.1"
  )
}
