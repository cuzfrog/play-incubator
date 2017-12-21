package controllers

import javax.inject._

import akka.util.ByteString
import play.api.cache.{Cached, SyncCacheApi}
import play.api.http.HttpEntity
import play.api.i18n._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Result(
      header = ResponseHeader(200),
      body = HttpEntity.Strict(ByteString(IndexPage.skeleton(request.messages)), Some("text/html"))
    )
  }
}

