package examples.petstore

import examples.petstore.model._

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Random

object util {
  def createPet(rand: Random): Pet = {
    Pet.apply(123.toLong, Category(), "name", Seq(""), Seq(Tag(Option(123.toLong), Option("tag"))), PetEnums.Status.Available)
  }
  def getApiKey(rand: Random): String = {
    "special-key"
  }
  def getUsername(rand: Random): String = {
    "user"
  }
  def getPassword(rand: Random): String = {
    "user"
  }
  def getPetId(rand: Random): Long = {
    123
  }
  def getPetTag(rand: Random): Seq[String] = {
    Seq("123")
  }
  def getPetStatus(rand: Random): Seq[String] = {
    val choice = rand.between(0, 2)
    if (choice==0) { Seq("available") }
    else if (choice==1) { Seq("pending") }
    else { Seq("sold") }
  }
  def getPetName(rand: Random): Option[String] = {
    Option("updatedName")
  }
  def getPetStatusOption(rand: Random): Option[String] = {
    val choice = rand.between(0, 2)
    if (choice==0) { Option("available") }
    else if (choice==1) { Option("pending") }
    else { Option("sold") }
  }
  def getLongApiKey(rand: Random): Long = {
    123
  }
  def getOrder(rand: Random): Order = {
    Order.apply(Option(456.toLong), Option(456.toLong), Option(2))
  }
  def getOrderId(rand: Random): Long = {
    456
  }
  def createUser(rand: Random): User = {
    User.apply(Option(123.toLong), Option("testuname"), Option("firstname"), Option("lastname"), Option("email@mail.com"), Option("password"), Option("00112233"), Option(1))
  }
  def createUsers(rand: Random): Seq[User] = {
    Seq(User.apply(Option(456.toLong), Option("testuname"), Option("firstname"), Option("lastname"), Option("email@mail.com"), Option("password"), Option("00112233"), Option(1)))
  }
  def getTestUsername(rand: Random): String = {
    "testuname"
  }
}
