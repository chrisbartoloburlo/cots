package examples.usersregistry

import examples.usersregistry.api.{AuthenticationApi, UsersApi}
import examples.usersregistry.model.{Token, TokenRequest}
import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }
  def setup: Unit = {
  }
  def teardown: Unit = {
    val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
    val response = AuthenticationApi.apply().create(util.getTokenRequest(new scala.util.Random(1))).send(backend)
    UsersApi.apply().destroy(response.body.asInstanceOf[Right[Any, Any]].value.asInstanceOf[Token].accessToken, util.getUserId(new scala.util.Random(1))).send(backend)
  }
  var now = System.nanoTime
  val d = new driver(setup, teardown, true,300, 1, 50, report)
  d.run()
  var timeElapsed = System.nanoTime - now
  println(timeElapsed)
}