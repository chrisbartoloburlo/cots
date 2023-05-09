/**
 * Common CRUD API
 *     A complete user registry, with access permissions,     JWT token, integration and unit tests, using     the RESTful API pattern. 
 *
 * The version of the OpenAPI document: v4.0.1
 * Contact: throyer.dev@gmail.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.usersregistry.api

import examples.usersregistry.model.ApiError
import examples.usersregistry.model.RefreshToken
import examples.usersregistry.model.RefreshTokenRequest
import examples.usersregistry.model.Token
import examples.usersregistry.model.TokenRequest
import examples.usersregistry.model.ValidationError
import examples.usersregistry.core.JsonSupport._
import sttp.client3._
import sttp.model.Method

object AuthenticationApi {

def apply(baseUrl: String = "http://localhost:8080") = new AuthenticationApi(baseUrl)
}

class AuthenticationApi(baseUrl: String) {

  /**
   * Expected answers:
   *   code 400 : Seq[ValidationError] (Bad Request)
   *   code 401 : ApiError (Unauthorized)
   *   code 200 : Token (OK)
   * 
   * @param tokenRequest 
   */
  def create(tokenRequest: TokenRequest
): Request[Either[ResponseException[String, Exception], Token], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/api/sessions")
      .contentType("application/json")
      .body(tokenRequest)
      .response(asJson[Token])

  /**
   * Expected answers:
   *   code 400 : Seq[ValidationError] (Bad Request)
   *   code 401 : ApiError (Unauthorized)
   *   code 200 : RefreshToken (OK)
   * 
   * @param refreshTokenRequest 
   */
  def refresh(refreshTokenRequest: RefreshTokenRequest
): Request[Either[ResponseException[String, Exception], RefreshToken], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/api/sessions/refresh")
      .contentType("application/json")
      .body(refreshTokenRequest)
      .response(asJson[RefreshToken])

}