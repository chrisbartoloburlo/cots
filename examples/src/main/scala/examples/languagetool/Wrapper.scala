package examples.languagetool

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }
  val seed=32
  def setup: Unit = {
//    val rand: Random = new Random(seed)
//    val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
//    val request = AuthTokensApi.apply().registerApiOauth2(util.getFormat(rand), util.getRegisterApiOauth2Request(rand))
//    val response = request.send(backend)
//    val cookies = response.headers("Set-Cookie")
//    val cookie = cookies.map(c => c.split(";")(0)).mkString("; ")
//    util.setApiKey(cookie)
  }
  def teardown: Unit = {
  }
  var now = System.nanoTime
  val d = new driver(setup, teardown, true,300, seed, 20, report)
  d.run()
  var timeElapsed = System.nanoTime - now
  println(timeElapsed)
}