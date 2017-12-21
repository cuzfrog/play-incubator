package models

import javax.inject.{Inject, Singleton}

import akka.actor.ActorSystem
import play.api.libs.concurrent.CustomExecutionContext

@Singleton
class NetworkExecutionContext @Inject()(system: ActorSystem)
  extends CustomExecutionContext(system, "network-dispatcher")


@Singleton
class DatabaseExecutionContext @Inject()(system: ActorSystem)
  extends CustomExecutionContext(system, "database-dispatcher")