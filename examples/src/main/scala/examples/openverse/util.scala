package examples.openverse

import examples.openverse.model.{AuthorizeApiOauth2Request, Image, ImageSearch, OAuth2AuthenticationSuccessful, OAuth2RegistrationSuccessful, RegisterApiOauth2Request}

import java.net.URI
import scala.annotation.tailrec
import scala.math.{abs, random}
import scala.util.Random

object util {
  def getRegisterApiOauth2Request(rand: Random): RegisterApiOauth2Request = {
//    RegisterApiOauth2Request("testing"+abs(rand.nextInt()), "rest-setts", "testing"+abs(rand.nextInt())+"@setts.com")
    RegisterApiOauth2Request("Chris", "rest-setts", "chrisbartolo227@gmail.com")
  }

  def getFormat(rand: Random): String = {
    "json"
  }
  def optString(string: String, rand: Random): Option[String] = {
    Option(string)
  }
  def const(string: String, rand: Random): String = {
    string
  }
  def constantInt(string: String, rand: Random): Option[Int] = {
    Option(string.toInt)
  }
  def randomInt(rand: Random): Option[Int]= {
    Option(rand.between(0,100))
  }
  def randomString(rand: Random): Option[String] = {
    Option(rand.alphanumeric.take(rand.between(1,10)).mkString)
  }
  def constBool(boolean: String, rand: Random): Boolean = {
    if(boolean=="true") true
    else false
  }
  var images: Seq[Image] = null
  def setImages(imageSearch: ImageSearch): Boolean = {
    images = imageSearch.results
    true
  }
  def getIdentifier(rand: Random): String = {
    images(rand.between(0, images.size-1)).id
  }
  var clientId: String = ""
  var clientSecret: String = ""
  def setRegistration(registration: OAuth2RegistrationSuccessful): Boolean = {
    clientId = registration.client_id
    clientSecret = registration.client_secret
    true
  }

  def getClientId(rand: Random): String = {
    clientId
  }

  def getClientSecret(rand: Random): String = {
    clientSecret
  }
  def getAuthorizeApiOauth2Request(rand: Random): AuthorizeApiOauth2Request = {
    new AuthorizeApiOauth2Request(clientId, clientSecret, "client_credentials")
  }
//  @tailrec
  def getImageUrl(rand:Random): String = {
    images.filter(_.url!=null)(rand.between(0, images.count(_.url != null)-1)).url.get.toString
//    val i = images(rand.between(0, images.size-1))
//    i.url match {
//      case Some(_) =>  i.url.get.toString
//      case None => getImageUrl(new Random(rand.nextInt))
//    }
  }
  var token = ""
  def setToken(authentication: OAuth2AuthenticationSuccessful): Boolean = {
    token=authentication.access_token
    true
  }
  def getToken(rand: Random): String = {
    "BsU4R2WSYd6fUR2BKKwDpNNzICifJB"
  }
}
