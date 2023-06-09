/**
 * User
 * Provide Customer login, register, retrieval, as well as card and address retrieval
 *
 * The version of the OpenAPI document: 
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.sockshop.api

import examples.sockshop.model.Address
import examples.sockshop.model.Card
import examples.sockshop.model.Customer
import examples.sockshop.model.Deleteresponse
import examples.sockshop.model.Getcardsresponse
import examples.sockshop.model.Getcustomersresponse
import examples.sockshop.model.Listaddressresponse
import examples.sockshop.model.Postaddress
import examples.sockshop.model.Postcard
import examples.sockshop.model.Register
import examples.sockshop.model.Statusresponse
import examples.sockshop.core.JsonSupport._
import sttp.client3._
import sttp.model.Method

object DefaultApi {

def apply(baseUrl: String = "http://localhost:80") = new DefaultApi(baseUrl)
}

class DefaultApi(baseUrl: String) {

  /**
   * Delete address
   * 
   * Expected answers:
   *   code 200 : Deleteresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param id ID of address to delete
   */
  def deleteAddress(apiKey: String, id: String
): Request[Either[ResponseException[String, Exception], Deleteresponse], Any] =
    basicRequest
      .method(Method.DELETE, uri"$baseUrl/addresses/${id}")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Deleteresponse])

  /**
   * Delete card
   * 
   * Expected answers:
   *   code 200 : Deleteresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param id ID of card to delete
   */
  def deleteCard(apiKey: String, id: String
): Request[Either[ResponseException[String, Exception], Deleteresponse], Any] =
    basicRequest
      .method(Method.DELETE, uri"$baseUrl/cards/${id}")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Deleteresponse])

  /**
   * Delete customer
   * 
   * Expected answers:
   *   code 200 : Deleteresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param id ID of customer to delete
   */
  def deleteCustomer(apiKey: String, id: String
): Request[Either[ResponseException[String, Exception], Deleteresponse], Any] =
    basicRequest
      .method(Method.DELETE, uri"$baseUrl/customers/${id}")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Deleteresponse])

  /**
   * Returns an address
   * 
   * Expected answers:
   *   code 200 : Address ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param id ID of address to fetch
   */
  def getAddress(apiKey: String, id: String
): Request[Either[ResponseException[String, Exception], Address], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/addresses/${id}")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Address])

  /**
   * Returns all addresses
   * 
   * Expected answers:
   *   code 200 : Listaddressresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   */
  def getAddresses(apiKey: String, 
): Request[Either[ResponseException[String, Exception], Listaddressresponse], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/addresses")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Listaddressresponse])

  /**
   * Returns a card
   * 
   * Expected answers:
   *   code 200 : Card ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param id ID of card to fetch
   */
  def getCard(apiKey: String, id: String
): Request[Either[ResponseException[String, Exception], Card], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/cards/${id}")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Card])

  /**
   * Return all cards
   * 
   * Expected answers:
   *   code 200 : Getcardsresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   */
  def getCards(apiKey: String, 
): Request[Either[ResponseException[String, Exception], Getcardsresponse], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/cards")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Getcardsresponse])

  /**
   * Returns a customer
   * 
   * Expected answers:
   *   code 200 : Customer ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param id ID of customer to fetch
   */
  def getCustomer(apiKey: String, id: String
): Request[Either[ResponseException[String, Exception], Customer], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/customers/${id}")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Customer])

  /**
   * Returns an address
   * 
   * Expected answers:
   *   code 200 : Listaddressresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param id ID of customer to fetch
   */
  def getCustomerAddresses(apiKey: String, id: String
): Request[Either[ResponseException[String, Exception], Listaddressresponse], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/customers/${id}/addresses")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Listaddressresponse])

  /**
   * Returns a customer
   * 
   * Expected answers:
   *   code 200 : Getcardsresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param id ID of customer to fetch
   */
  def getCustomerCards(apiKey: String, id: String
): Request[Either[ResponseException[String, Exception], Getcardsresponse], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/customers/${id}/cards")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Getcardsresponse])

  /**
   * Returns all customers
   * 
   * Expected answers:
   *   code 200 : Getcustomersresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   */
  def getCustomers(apiKey: String, 
): Request[Either[ResponseException[String, Exception], Getcustomersresponse], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/customers")
      .contentType("application/json")
      .header("Cookie", apiKey)
      .response(asJson[Getcustomersresponse])

  /**
   * Return logged in user
   * 
   * Expected answers:
   *   code 200 : Getcustomersresponse ()
   * 
   * Available security schemes:
   *   basicAuth (http)
   */
  def getLogin(username: String, password: String, 
): Request[Either[ResponseException[String, Exception], Getcustomersresponse], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/login")
      .contentType("application/json")
      .auth.basic(username, password)
      .response(asJson[Getcustomersresponse])

  /**
   * Create new address
   * 
   * Expected answers:
   *   code 200 : Statusresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param address Address
   */
  def setAddress(apiKey: String, address: Postaddress
): Request[Either[ResponseException[String, Exception], Statusresponse], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/addresses")
      .contentType("application/json;charset=UTF-8")
      .header("Cookie", apiKey)
      .body(address)
      .response(asJson[Statusresponse])

  /**
   * Create new card
   * 
   * Expected answers:
   *   code 200 : Statusresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param card Credit card
   */
  def setCard(apiKey: String, card: Postcard
): Request[Either[ResponseException[String, Exception], Statusresponse], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/cards")
      .contentType("application/json;charset=UTF-8")
      .header("Cookie", apiKey)
      .body(card)
      .response(asJson[Statusresponse])

  /**
   * Register new user
   * 
   * Expected answers:
   *   code 200 : Statusresponse ()
   * 
   * Available security schemes:
   *   cookieAuth (apiKey)
   * 
   * @param register register object
   */
  def setUser(apiKey: String, register: Register
): Request[Either[ResponseException[String, Exception], Statusresponse], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/register")
      .contentType("application/json;charset=UTF-8")
      .header("Cookie", apiKey)
      .body(register)
      .response(asJson[Statusresponse])

}
