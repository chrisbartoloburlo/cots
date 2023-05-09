package examples.cwa

import examples.cwa.model.RegistrationToken
import examples.featuresservice.model.{Feature, Product, ProductConfiguration}

import scala.util.Random

object util {
  def constant(string: String, rand: Random): Option[String] = {
    Option(string)
  }
  def getRegistrationToken(rand: Random): RegistrationToken = {
    RegistrationToken("1ea6ce8a-9740-41ea-bb37-0242ac130002", Option("1"))
  }
}
