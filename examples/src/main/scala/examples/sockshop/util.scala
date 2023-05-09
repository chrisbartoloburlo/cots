package examples.sockshop

import examples.sockshop.model._

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Random

object util {
  def const(string: String, rand: Random): String ={
    string
  }

  private var apiKey: String = ""
  def setApiKey(apiKey: String): Unit = {
    this.apiKey=apiKey
  }
  def getApiKey(rand: Random): String = {
    apiKey
  }

  def rndRegister(rand: Random): Register = {
    val id = Random.nextInt
    Register(f"testUser${id}", "testPassword",
             Some(f"test${id}@domain.com"))
  }

  private var userId = ""
  def setUserId(resp: Statusresponse): Boolean = {
    userId = resp.id
    true
  }
  def getUserId(rand: Random): String = {
    userId
  }

  def checkCustomerExists(customers: Getcustomersresponse, username: String): Boolean = {
    (!customers._embedded.customer.isEmpty) &&
      customers._embedded.customer.get.exists(c => c.username == username)
  }

  def checkCustomerNotExists(customers: Getcustomersresponse, username: String): Boolean = {
    customers._embedded.customer.isDefined &&
      customers._embedded.customer.get.forall(c => c.username != username)
  }

  def checkCardsEmpty(cards: Getcardsresponse): Boolean = {
    cards._embedded.card.isEmpty || (cards._embedded.card.get.size == 0)
  }
}
