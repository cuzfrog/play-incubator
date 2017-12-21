import com.github.cuzfrog.sbttmpfs.SbtTmpfsPlugin.autoImport._
//import com.typesafe.sbt.packager.Keys._
//import com.typesafe.sbt.packager.debian.DebianPlugin.autoImport.Debian
import com.typesafe.sbt.web.SbtWeb.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import play.sbt.PlayImport._
import sbt.Keys._
import sbt._
import spray.revolver.RevolverPlugin.autoImport._
//import webscalajs.WebScalaJS.autoImport._
//import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object AppSettings {

  val commonSettings = Seq(
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-unchecked"
    ),
    resolvers ++= Seq(
      //      Resolver.bintrayRepo("cuzfrog", "maven"),
      //      Opts.resolver.sonatypeSnapshots
    ),
    licenses += ("Apache-2.0", url("http://www.opensource.org/licenses/apache2.0.php")),
    tmpfsDirectoryMode := TmpfsDirectoryMode.Mount,
    logBuffered in Test := false,
    parallelExecution in Test := false,
    traceLevel := 100
  )

  val clientSettings = {
    val jestFramework: TestFramework = new TestFramework("sjest.JestFramework")
    Seq(
      name := "songbird-client",
      scalacOptions ++= Seq(
        "-P:scalajs:sjsDefinedByDefault"
      ),
      scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
      scalaJSUseMainModuleInitializer := true,
      scalaJSStage in Test := FastOptStage,
      libraryDependencies ++= {
        val sriVersion = "0.2.0"
        Seq(
          "org.scala-js" %%% "scalajs-java-time" % "0.2.3",
          "org.scala-js" %%% "scalajs-dom" % "0.9.4",
          "io.suzaku" %%% "diode" % "1.1.2",
          "com.github.cuzfrog" %%% "simple-sri" % sriVersion,
          "com.github.cuzfrog" %%% "simple-sri-diode" % sriVersion,
          "com.github.cuzfrog" %%% "simple-sri-test-utils" % sriVersion % Test,
          "com.github.cuzfrog" %%% "sjest" % "0.2.0" % Test
        )
      },
      testFrameworks += jestFramework,
      testOptions += Tests.Argument(jestFramework,
        s"-opt.js.path:${(artifactPath in Test in fastOptJS).value}")
    )
  }

  val serverSettings = Seq(
    name := "songbird-server",
    //mainClass in reStart := Some("Server"),
    managedClasspath in Runtime += {
      /*
      add assets to resources, so akka-http is able to getFromResource
      modules(lib) in webJar will be automatically incorporated in the packageBin
      This is required for source map support.
      */
      (packageBin in Assets).value
    },
    libraryDependencies ++= Seq(
      guice, ehcache, ws,
      "com.typesafe.play" %% "twirl-api" % "1.3.8",
      "com.lihaoyi" %% "scalatags" % "0.6.7",
      //"org.reactivemongo" %% "reactivemongo" % "0.12.6",
      "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
    )
  )

  val sharedSettings = Seq(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %%% "play-json" % "2.6.3"
    )
  )
}
