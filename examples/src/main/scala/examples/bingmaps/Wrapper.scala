package examples.bingmaps

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }
  val seed=1
  def setup: Unit = {
  }
  def teardown: Unit = {
  }
  var now = System.nanoTime
  val d = new driver(setup, teardown, true,300, seed, 1, report)
  d.run()
  var timeElapsed = System.nanoTime - now
  println(timeElapsed)
}