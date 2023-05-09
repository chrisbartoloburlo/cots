/**
 * cwa-verification-server
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.5.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.cwa.api

import examples.cwa.model.RegistrationToken
import examples.cwa.model.TestResult
import examples.cwa.core.JsonSupport._
import sttp.client3._
import sttp.model.Method

object ExternalTestStateControllerApi {

def apply(baseUrl: String = "http://localhost:8080") = new ExternalTestStateControllerApi(baseUrl)
}

class ExternalTestStateControllerApi(baseUrl: String) {

  /**
   * Gets the result of COVID-19 Test. If the RegistrationToken belongs to a TeleTan the result is always positive
   * 
   * Expected answers:
   *   code 500 :  (Internal Server Error)
   *   code 400 :  (Bad Request)
   *   code 405 :  (Method Not Allowed)
   *   code 415 :  (Unsupported Media Type)
   *   code 200 : TestResult (Testresult retrieved)
   *   code 403 : TestResult (TestResult of dob hash does not equal to TestResult of hash)
   * 
   * @param registrationToken 
   * @param cwaFake 
   */
  def getTestState(registrationToken: RegistrationToken, cwaFake: Option[String] = None
): Request[Either[ResponseException[String, Exception], TestResult], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/version/v1/testresult")
      .contentType("application/json")
      .header("cwa-fake", cwaFake.toString)
      .body(registrationToken)
      .response(asJson[TestResult])

}