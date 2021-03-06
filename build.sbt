val ProjectName = "agile-play-logback"

lazy val commonSettings = Seq(
  organization := "au.com.agiledigital",
  scalaVersion := "2.11.8",
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-encoding", "UTF-8"),
  scalacOptions ++= Seq(
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-feature", // Emit warning and location for usages of features that should be imported explicitly.
    "-unchecked", // Enable additional warnings where generated code depends on assumptions.
    "-Xfatal-warnings", // Fail the compilation if there are any warnings.
    "-Xlint", // Enable recommended additional warnings.
    "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver.
    "-Ywarn-dead-code", // Warn when dead code is identified.
    "-Ywarn-inaccessible", // Warn about inaccessible types in method signatures.
    "-Ywarn-nullary-override", // Warn when non-nullary overrides nullary, e.g. def foo() over def foo.
    "-Ywarn-numeric-widen" // Warn when numerics are widened.
  ),
  // Disable scaladoc generation when building dist.
  sources in(Compile, doc) := Seq.empty,
  updateOptions := updateOptions.value.withCachedResolution(true),
  concurrentRestrictions in Global := Seq(
    Tags.limitSum(1, Tags.Compile, Tags.Test),
    Tags.limitAll(2)
  ),
  libraryDependencies ++= Dependencies.PlayDependencies,
  libraryDependencies ++= Dependencies.SpecsDependencies
) ++ Analysis.analysisSettings ++ Formatting.formattingSettings ++ Publishing.settings

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := ProjectName
  )
