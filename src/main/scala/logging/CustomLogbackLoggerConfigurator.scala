/*
 * Copyright (C) 2009-2016 Lightbend Inc. <https://www.lightbend.com>
 */
package logging

import java.io.File
import java.net.URL

import ch.qos.logback.classic._
import ch.qos.logback.classic.joran._
import ch.qos.logback.core.util._
import org.slf4j.LoggerFactory
import play.api._

class CustomLogbackLoggerConfigurator extends LoggerConfigurator {

  // Grab the logger factory to work around the class loading issue when using AspectJ.
  val loggerFactory = LoggerFactory.getILoggerFactory

  /**
    * Initialize the Logger when there's no application ClassLoader available.
    */
  def init(rootPath: java.io.File, mode: Mode.Mode): Unit = {
    val properties = Map("application.home" -> rootPath.getAbsolutePath)
    val resourceName = "logback-play-default.xml"
    val resourceUrl = Option(this.getClass.getClassLoader.getResource(resourceName))
    configure(properties, resourceUrl)
  }

  /**
    * Reconfigures the underlying logback infrastructure.
    */
  def configure(env: Environment): Unit = {
    val properties = Map("application.home" -> env.rootPath.getAbsolutePath)

    // Get an explicitly configured resource URL
    // Fallback to a file in the conf directory if the resource wasn't found on the classpath
    def explicitResourceUrl = sys.props.get("logger.resource").map { r =>
      env.resource(r).getOrElse(new File(env.getFile("conf"), r).toURI.toURL)
    }

    // Get an explicitly configured file URL
    def explicitFileUrl = sys.props.get("logger.file").map(new File(_).toURI.toURL)

    // logback.xml is the documented method, logback-play-default.xml is the fallback that Play uses
    // if no other file is found
    def resourceUrl = env.resource("logback.xml")

    val configUrl = explicitResourceUrl orElse explicitFileUrl orElse resourceUrl

    configure(properties, configUrl)
  }

  /**
    * Reconfigures the underlying logback infrastructure.
    */
  def configure(properties: Map[String, String], config: Option[URL]): Unit = synchronized {
    // Redirect JUL -> SL4FJ
    {
      import java.util.logging._

      import org.slf4j.bridge._

      Option(java.util.logging.Logger.getLogger("")).foreach { root =>
        root.setLevel(Level.FINEST)
        root.getHandlers.foreach(root.removeHandler)
      }

      SLF4JBridgeHandler.install()
    }

    // Configure logback
    {

      LoggerFactory.getILoggerFactory match {
        case ctx: LoggerContext =>
          val configurator = new JoranConfigurator
          configurator.setContext(ctx)
          ctx.reset()

          // Ensure that play.Logger and play.api.Logger are ignored when detecting file name and line number for
          // logging
          val frameworkPackages = ctx.getFrameworkPackages
          frameworkPackages.add(classOf[play.Logger].getName)
          frameworkPackages.add(classOf[play.api.Logger].getName)

          properties.foreach {
            case (name, value) => ctx.putProperty(name, value)
          }

          config match {
            case Some(url) => configurator.doConfigure(url)
            case None =>
              System.err.println("Could not detect a logback configuration file, not configuring logback.") // scalastyle:ignore
          }

          StatusPrinter.printIfErrorsOccured(ctx)
        case _ =>
          System.err.println(s"LoggerFactory ILoggerFactory [${LoggerFactory.getILoggerFactory}] not an instance of LoggerContext.") // scalastyle:ignore
      }
    }
  }

  /**
    * Shutdown the logger infrastructure.
    */
  def shutdown(): Unit = {

    LoggerFactory.getILoggerFactory match {
      case ctx: LoggerContext => ctx.stop()
      case _ =>
    }

    org.slf4j.bridge.SLF4JBridgeHandler.uninstall()
  }

}
