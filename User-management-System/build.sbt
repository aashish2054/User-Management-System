name := "User-management-System"

version := "0.1"

scalaVersion := "2.13.5"
coverageEnabled := true

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.5" % Test

libraryDependencies ++=Seq(
  "org.scalatest" %% "scalatest" % "3.2.2" % "test",
  "org.mockito" %% "mockito-scala" % "1.5.12" % "test"
)
