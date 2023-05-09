/**
 * Sistema de Gestão Hospital API
 * Documentação da API de acesso aos endpoints da GestaoHospitalAPI - Aceleradev Brasil  Jornada de desafios da Aceleradev Brasil - CodeNation
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.gestaohospital.api

import examples.gestaohospital.model.HospitalDTO
import examples.gestaohospital.core.JsonSupport._
import sttp.client3._
import sttp.model.Method

object HospitalResourceApi {

def apply(baseUrl: String = "http://localhost:8080") = new HospitalResourceApi(baseUrl)
}

class HospitalResourceApi(baseUrl: String) {

  /**
   * Expected answers:
   *   code 200 : String (OK)
   *   code 401 :  (Unauthorized)
   *   code 204 :  (No Content)
   *   code 403 :  (Forbidden)
   * 
   * @param hospitalId hospital_id
   */
  def deleteByIdUsingDELETE(hospitalId: String
): Request[Either[ResponseException[String, Exception], String], Any] =
    basicRequest
      .method(Method.DELETE, uri"$baseUrl/v1/hospitais/${hospitalId}")
      .contentType("application/json")
      .response(asJson[String])

  /**
   * Expected answers:
   *   code 200 : Seq[HospitalDTO] (OK)
   *   code 401 :  (Unauthorized)
   *   code 403 :  (Forbidden)
   *   code 404 :  (Not Found)
   */
  def findAllUsingGET(
): Request[Either[ResponseException[String, Exception], Seq[HospitalDTO]], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/v1/hospitais/")
      .contentType("application/json")
      .response(asJson[Seq[HospitalDTO]])

  /**
   * Expected answers:
   *   code 200 : HospitalDTO (OK)
   *   code 401 :  (Unauthorized)
   *   code 403 :  (Forbidden)
   *   code 404 :  (Not Found)
   * 
   * @param hospitalId hospital_id
   */
  def findByIdUsingGET(hospitalId: String
): Request[Either[ResponseException[String, Exception], HospitalDTO], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/v1/hospitais/${hospitalId}")
      .contentType("application/json")
      .response(asJson[HospitalDTO])

  /**
   * Expected answers:
   *   code 200 : HospitalDTO (OK)
   *   code 401 :  (Unauthorized)
   *   code 403 :  (Forbidden)
   *   code 404 :  (Not Found)
   * 
   * @param lat lat
   * @param lon lon
   * @param raioMaximo raioMaximo
   */
  def hospitalMaisProximoUsingGET(lat: String, lon: String, raioMaximo: String
): Request[Either[ResponseException[String, Exception], HospitalDTO], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/v1/hospitais/maisProximo?lat=${ lat }&lon=${ lon }&raioMaximo=${ raioMaximo }")
      .contentType("application/json")
      .response(asJson[HospitalDTO])

  /**
   * Expected answers:
   *   code 200 : HospitalDTO (OK)
   *   code 201 :  (Created)
   *   code 401 :  (Unauthorized)
   *   code 403 :  (Forbidden)
   *   code 404 :  (Not Found)
   * 
   * @param objDTO objDTO
   */
  def insertUsingPOST(objDTO: HospitalDTO
): Request[Either[ResponseException[String, Exception], HospitalDTO], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/v1/hospitais/")
      .contentType("application/json")
      .body(objDTO)
      .response(asJson[HospitalDTO])

  /**
   * Expected answers:
   *   code 200 : String (OK)
   *   code 201 :  (Created)
   *   code 401 :  (Unauthorized)
   *   code 403 :  (Forbidden)
   *   code 404 :  (Not Found)
   * 
   * @param id id
   * @param productId productId
   * @param quantidade quantidade
   */
  def transferenciaProdutoUsingPOST(id: String, productId: String, quantidade: String
): Request[Either[ResponseException[String, Exception], String], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/v1/hospitais/${id}/transferencia/${productId}")
      .contentType("application/json")
      .body(quantidade)
      .response(asJson[String])

  /**
   * Expected answers:
   *   code 200 : HospitalDTO (OK)
   *   code 201 :  (Created)
   *   code 401 :  (Unauthorized)
   *   code 403 :  (Forbidden)
   *   code 404 :  (Not Found)
   * 
   * @param hospitalId hospital_id
   * @param objDTO objDTO
   */
  def updateUsingPUT(hospitalId: String, objDTO: HospitalDTO
): Request[Either[ResponseException[String, Exception], HospitalDTO], Any] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/v1/hospitais/${hospitalId}")
      .contentType("application/json")
      .body(objDTO)
      .response(asJson[HospitalDTO])

  /**
   * Expected answers:
   *   code 200 : Map[String, Int] (OK)
   *   code 401 :  (Unauthorized)
   *   code 403 :  (Forbidden)
   *   code 404 :  (Not Found)
   * 
   * @param id id
   */
  def verificaLeitosDisponiveisUsingGET(id: String
): Request[Either[ResponseException[String, Exception], Map[String, Int]], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/v1/hospitais/${id}/leitos")
      .contentType("application/json")
      .response(asJson[Map[String, Int]])

}