package examples.usersregistry

import examples.petclinic.model.{Owner, OwnerFields}
import examples.usersregistry.model.{CreateUserProps, PageUser, RecoveryRequest, Token, TokenRequest, UpdateUserProps, User}

import scala.util.Random

object util {
  def const(string: String, rand: Random): String ={
    string
  }
  def optDescr(rand: Random): Option[String] = {
    Option(rand.alphanumeric.take(20).mkString)
  }
  def rand(min: String, max: String, rand: Random): Int ={
    min.toInt + rand.nextInt( (max.toInt - min.toInt) + 1 )
  }
  val email = "admin@email.com"
  var pw = "admin"
  def getTokenRequest(rand: Random):TokenRequest = {
//    pw = rand.alphanumeric.take(10).mkString
    new TokenRequest(email, pw)
  }
  var token: Token = null
  def getAccessToken(rand: Random): String = {
    token.accessToken
  }
  def setToken(token: Token): Boolean = {
    this.token = token
    true
  }

  var createdUserName = "Test1"
  var createdUserEmail = "test1@mail.com"
  var createUserPassword = "12345678"
  def getUserProps(rand: Random): CreateUserProps = {
    CreateUserProps(createdUserName, createdUserEmail, createUserPassword)
  }
  var user: User = null
  var userId: Long = 0
  def testAndSetUser(user: User): Boolean = {
    if(user.name == createdUserName && user.email==createdUserEmail){
      this.user = user
      this.userId = user.id
      true
    } else {
      false
    }
  }
  def getUserId(rand: Random): Long = {
    this.userId
  }
  def checkIndex(pageUser: PageUser): Boolean = {
    pageUser.content.contains(this.user)
  }
  def getUpdateUserProps(rand: Random): UpdateUserProps = {
    createdUserName = "Test2"
    createdUserEmail = "test2@mail.com"
    UpdateUserProps("Test2", "test2@mail.com")
  }
  def getRecoveryRequest(rand: Random): RecoveryRequest = {
    RecoveryRequest("chrisbartolo227@gmail.com")
  }
}
