package examples.gitlab

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }
  def setup: Unit = {
  }
  def teardown: Unit = {
  }
  var now = System.nanoTime
  val d = new driver(setup, teardown, true,300, 1, 10, report)
  d.run()
  var timeElapsed = System.nanoTime - now
  println(timeElapsed)
}