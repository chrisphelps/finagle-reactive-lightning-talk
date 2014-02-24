name := "user"

version := "1.0"

scalaVersion := "2.9.2"

libraryDependencies += "com.twitter" %% "finagle-http" % "6.2.0"

com.twitter.scrooge.ScroogeSBT.newSettings

libraryDependencies ++= Seq(
  "org.apache.thrift" % "libthrift" % "0.8.0",
  "com.twitter" %% "scrooge-core" % "3.12.2",
  "com.twitter" %% "finagle-thrift" % "6.5.0"
)

