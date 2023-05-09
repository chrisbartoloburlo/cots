package examples.sockshop

import examples.petclinic.api.{OwnerApi, PettypesApi, SpecialtyApi, VetsApi}
import examples.sockshop.api.DefaultApi
import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

import scala.language.postfixOps
import scala.sys.process._
import scala.util.Random

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }

  def setup: Unit = {
    val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
    val request = DefaultApi.apply().getLogin("user1", "password")
    val response = request.send(backend)
    val cookies = response.headers("Set-Cookie")
    val cookie = cookies.map(c => c.split(";")(0)).mkString("; ")
    util.setApiKey(cookie)
  }

  def teardown: Unit = {
  }

  val d = new driver(setup, teardown, true, 300, 1, 50, report)
  d.run()
}
