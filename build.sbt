import AppSettings._
import MyTasks._

inThisBuild(Seq(
  shellPrompt := { state => Project.extract(state).currentRef.project + "> " },
  scalaVersion := "2.12.4",
  organization := "com.github.cuzfrog",
  version := "0.0.1-SNAPSHOT"
))

val root = project.in(file("."))
  .aggregate(client, server)

lazy val server = project.dependsOn(sharedJVM)
  .enablePlugins(PlayScala, WebScalaJSBundlerPlugin)
  .settings(serverSettings, commonSettings)
  .settings(
    scalaJSProjects := Seq(client),
    pipelineStages in Assets := Seq(scalaJSPipeline),
    npmAssets ++= NpmAssets.ofProject(client){ nodeModules=>
      val vendors = Seq("bulma/css",
        "font-awesome/css",
        "font-awesome/fonts")
      vendors.map(nodeModules / _).allPaths
    }.value,
    addCommandAlias("packDeb", ";reload;clean;" +
      "set isDevMode := false;debian:packageBin;set isDevMode := true")
  )

lazy val client = project.dependsOn(sharedJS)
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin, ScalaJSWeb)
  .settings(clientSettings, commonSettings)

lazy val shared = crossProject.crossType(CrossType.Pure)
  .settings(sharedSettings, commonSettings)

lazy val sharedJVM = shared.jvm
lazy val sharedJS = shared.js
// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.bi

//mount tmpfs:
val tmpfsProjects = Vector(root, server, client, sharedJVM, sharedJS)
val cmd = tmpfsProjects.map(p => s";${p.id}/tmpfsOn").mkString
addCommandAlias("tmpfsAllOn", cmd)