// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.1")

// The scala style plugin
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

// Wart removers
addSbtPlugin("org.brianmckenna" % "sbt-wartremover" % "0.14")

// Play wart removers
addSbtPlugin("org.danielnixon" % "sbt-playwarts" % "0.18")

// For running the project with Kamon metrics that require weaving
addSbtPlugin("io.kamon" % "aspectj-play-runner" % "0.1.3")

// For formatting of the source code.
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

// For performing releases
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.3")

// For publishing to sonatype
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.1")

// And signing artefacts to publish.
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

// Jacoco coverage plugin.
addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")
