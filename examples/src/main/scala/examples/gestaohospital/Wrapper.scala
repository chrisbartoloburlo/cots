package examples.gestaohospital

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }
  def setup: Unit = {
  }
  def teardown: Unit = {
  }
  val d = new driver(setup, teardown, true,300, 1, 50, report)
  d.run()
}