package controllers

import play.api.i18n.{Messages, MessagesProvider}

import scalatags.Text.all._

object IndexPage {
  def skeleton(implicit messagesProvider: MessagesProvider): String =
    html(
      head(
        scalatags.Text.tags2.title(Messages("index.title")),
        link(rel := "stylesheet", href := "assets/bulma/css/bulma.css"),
        link(rel := "stylesheet", href := "assets/font-awesome/css/font-awesome.min.css"),
        script(`type` := "text/javascript", src := jsLibrary),
        script(`type` := "text/javascript", src := jsLoader)
      ),
      body(
        div(id := "app"),
        script(`type` := "text/javascript", src := jsOpt)
      )
    ).render

  private def jsPrefix = "client-fastopt"
  private def jsLibrary = s"assets/$jsPrefix-library.js"
  private def jsLoader = s"assets/$jsPrefix-loader.js"
  private def jsOpt = s"assets/$jsPrefix.js"

}
