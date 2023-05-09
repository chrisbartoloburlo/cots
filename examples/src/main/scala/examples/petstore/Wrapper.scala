package examples.petstore

import examples.petstore.api._
import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

import scala.language.postfixOps
import scala.util.Random

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }

  class setupThread extends Runnable {
    def run(): Unit = {
    }
  }
  def setup: Unit = {
  }
  def teardown: Unit = {

  }
  val d = new driver(setup, teardown, true,300, 1, 100, report)
  d.run()
}