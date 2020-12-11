name := "poker-hand-strength-evaluator"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.2"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"

logBuffered in Test := false

mainClass in (Compile, run) := Some("com.assignment.poker.ConsolePokerEvaluatorRunner")
