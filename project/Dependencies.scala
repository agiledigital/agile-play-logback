import sbt._

object Dependencies {

  val CompileAndTest = "test->test;compile->compile"

  val Specs2Version = "3.6.5"

  val PlayVersion = "2.5.3"

  val PlayDependencies = Seq(
    "com.typesafe.play" %% "play" % Dependencies.PlayVersion % Provided,
    "ch.qos.logback" % "logback-classic" % "1.1.7"
  )

  val SpecsDependencies = Seq(
    "org.specs2" %% "specs2-core" % Specs2Version % Test,
    "org.specs2" %% "specs2-junit" % Specs2Version % Test,
    "org.specs2" %% "specs2-matcher-extra" % Specs2Version % Test,
    "org.specs2" %% "specs2-analysis" % Specs2Version % Test,
    "org.specs2" %% "specs2-mock" % Specs2Version % Test,
    "com.typesafe.play" %% "play-specs2" % PlayVersion % Test,
    "de.leanovate.play-mockws" %% "play-mockws" % "2.5.0" % Test
  )
}
