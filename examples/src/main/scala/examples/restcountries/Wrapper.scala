package examples.restcountries

import examples.sockshop.api.DefaultApi
import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

import scala.language.postfixOps

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }

  def setup: Unit = {
  }

  def teardown: Unit = {
  }

  val d = new driver(setup, teardown, true, 300, 1, 200, report)
  d.run()
}
