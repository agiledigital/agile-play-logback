import de.johoop.jacoco4sbt.JacocoPlugin.jacoco
import org.danielnixon.playwarts.PlayWart
import org.scalastyle.sbt.ScalastylePlugin._
import sbt.Keys._
import sbt._
import wartremover.WartRemover.autoImport._

object Analysis {

  lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

  lazy val scalaStyleSettings = Seq(
    scalastyleFailOnError := true,
    compileScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value,
    (test in Test) <<= (test in Test) dependsOn compileScalastyle
  )

  lazy val coverageSettings = Seq(
  )  ++ jacoco.settings

  lazy val wartsSettings = Seq(
    wartremoverErrors in(Compile, compile) ++= Seq(
      Wart.FinalCaseClass,
      Wart.Null,
      Wart.TryPartial,
      Wart.Var,
      Wart.OptionPartial,
      Wart.ListOps,
      Wart.EitherProjectionPartial,
      Wart.Any2StringAdd,
      Wart.AsInstanceOf,
      Wart.ExplicitImplicitTypes,
      Wart.MutableDataStructures,
      Wart.Return,
      Wart.AsInstanceOf,
      Wart.IsInstanceOf)
  )

  lazy val playWartsSettings = Seq(
    // Play Framework
    wartremoverWarnings in (Compile, compile) ++= Seq(
      PlayWart.CookiesPartial,
      PlayWart.FlashPartial,
      PlayWart.FormPartial,
      PlayWart.HeadersPartial,
      PlayWart.JsLookupResultPartial,
      PlayWart.JsReadablePartial,
      PlayWart.LangObject,
      PlayWart.MessagesObject,
      PlayWart.PlayGlobalExecutionContext,
      PlayWart.SessionPartial),

    // Bonus Warts
    wartremoverWarnings in (Compile, compile) ++= Seq(
      PlayWart.DateFormatPartial,
      PlayWart.FutureObject,
      PlayWart.GenMapLikePartial,
      PlayWart.GenTraversableLikeOps,
      PlayWart.GenTraversableOnceOps,
      PlayWart.OptionPartial,
      PlayWart.ScalaGlobalExecutionContext,
      PlayWart.StringOpsPartial,
      PlayWart.TraversableOnceOps,
      PlayWart.UntypedEquality)

  )

  lazy val analysisSettings = scalaStyleSettings ++ coverageSettings ++ wartsSettings ++ playWartsSettings
}