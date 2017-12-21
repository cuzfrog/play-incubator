package controllers

import play.api.i18n.{Messages, MessagesProvider}

import scalatags.Text.all._

object IndexPage {
  def skeleton(implicit messagesProvider: MessagesProvider): String =
    html(
      head(
        scalatags.Text.tags2.title(Messages("index.title")),
        link(rel := "stylesheet", href := "assets/vendor/css/bulma.css"),
        //link(rel := "stylesheet", href := "assets/addition.css"),
        link(rel := "stylesheet", href := "assets/vendor/css/font-awesome.min.css"),
        script(`type` := "text/javascript", src := "assets/vendor/vendor.bundle.js")
      ),
      body(
        div(id := "app"),
        script(`type` := "text/javascript", src := jsUrl)
      )
    ).render

  private def jsUrl = "assets/client-fastopt.js"
}
