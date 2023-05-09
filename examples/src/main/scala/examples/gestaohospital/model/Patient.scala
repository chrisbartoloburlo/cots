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
package examples.gestaohospital.model

import java.time.OffsetDateTime

case class Patient(
  active: Option[Boolean] = None,
  birthDate: Option[OffsetDateTime] = None,
  cpf: Option[String] = None,
  entryDate: Option[OffsetDateTime] = None,
  exitDate: Option[OffsetDateTime] = None,
  gender: Option[String] = None,
  id: Option[String] = None,
  location: Option[Location] = None,
  name: Option[String] = None
)
