package models

import com.google.inject.AbstractModule
import com.google.inject.name.Names

class Module extends AbstractModule {
  def configure(): Unit = {
    bind(classOf[StreamApi]).to(classOf[StreamImpl])
  }
}
