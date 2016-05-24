[![Build Status](https://travis-ci.org/agiledigital/agile-play-logback.svg?branch=master)](https://travis-ci.org/agiledigital/agile-play-logback)

Temporary fork of the Play Framework Logback module. Exists as a workaround
to https://github.com/playframework/playframework/issues/5997 that prevents
using the default logback module with AspectJ weaving enabled.

To use, disable the default logback module activated by the play plugin:

```scala
lazy val api = (project in file(".")).
  enablePlugins(PlayScala).
  // Disable the standard Play Logback module to work around https://github.com/playframework/playframework/issues/5997#
  disablePlugins(PlayLogback)
```

Then add the dependency on this module:

```scala
libraryDependencies += "au.com.agiledigital" %% "agile-play-logback" % "0.0.1"
```
