name := "Operator"

version := "1.0"

scalaVersion := "2.10.4"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.2.1"

libraryDependencies += "org.apache.spark" %% "spark-graphx" % "1.2.1"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

mainClass in (Compile, run) := Some("renq.tough.Operator")

mainClass in (Compile, packageBin) := Some("renq.tough.Operator")
