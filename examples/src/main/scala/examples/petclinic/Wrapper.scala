package examples.petclinic

import examples.petclinic.api.{OwnerApi, PettypesApi, SpecialtyApi, VetApi, VetsApi, VisitApi}
import sttp.client3.{HttpURLConnectionBackend, Identity, SttpBackend}

import java.util.concurrent.TimeUnit
import scala.language.postfixOps
import scala.sys.process._
import scala.util.Random

object Wrapper extends App{

  def report(msg: String): Unit = {
    print(msg)
  }

  class setupThread extends Runnable {
    def run(): Unit = {
//      s"java -jar ${System.getProperty("user.dir")}/examples/src/main/scala/examples/petclinic/spring-petclinic-rest-2.6.2.jar"!
    }
  }
  def setup: Unit = {
//    "pkill -f \"/usr/bin/java -jar "+System.getProperty("user.dir")+"/examples/src/main/scala/examples/petclinic/spring-petclinic-rest-2.6.2.jar\""!;
//    Thread.sleep(6000)
//    new Thread(new setupThread).start()
//    Thread.sleep(6000)
  }
  def teardown: Unit = {
    val backend: SttpBackend[Identity, Any] = HttpURLConnectionBackend()
    OwnerApi.apply().deleteOwner(util.getOwnerId(new scala.util.Random(1))).send(backend)
    VetsApi.apply().deleteVet(util.getVetId(new Random(1))).send(backend)
    PettypesApi.apply().deletePetType(util.getPetTypeId(new Random(1))).send(backend)
    SpecialtyApi.apply().deleteSpecialty(util.getSpecialtyId(new Random(1))).send(backend)
    VisitApi.apply().deleteVisit(util.getVisitId(new Random(1))).send(backend)
  }
//  var now = System.nanoTime
  val d = new driver(setup, teardown, true,300, 1, 200, report)
  d.run()
//  var timeElapsed = System.nanoTime - now
//  println(TimeUnit.SECONDS.convert(timeElapsed, TimeUnit.NANOSECONDS))
//  "pkill -f \"/usr/bin/java -jar "+System.getProperty("user.dir")+"/examples/src/main/scala/examples/petclinic/spring-petclinic-rest-2.6.2.jar\""!
}