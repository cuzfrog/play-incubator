import com.typesafe.sbt.web.pipeline.Pipeline.Stage
import sbt._

object MyTasks {
  val webpackPipeline: sbt.TaskKey[Stage] = taskKey[Stage]("Webpack dependencies into play assets.")
}
