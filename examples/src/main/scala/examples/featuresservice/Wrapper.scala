package examples.featuresservice

import examples.featuresservice.api.DefaultApi
import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

import scala.concurrent.duration.Duration

object Wrapper {
  def main(args: Array[String]): Unit = {
    val ec = scala.concurrent.ExecutionContext.global
    val timeout = Duration.Inf

    def report(msg: String): Unit = {
      print(msg)
    }

    def setup: Unit = {
    }

    def teardown: Unit = {
      val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
      DefaultApi.apply().deleteProductByName("Product_1").send(backend)
    }

    var now = System.nanoTime
    val d = new driver(setup, teardown, true, 300, 1, 150, report)
    d.run()
    var timeElapsed = System.nanoTime - now
    println(timeElapsed)
  }
}