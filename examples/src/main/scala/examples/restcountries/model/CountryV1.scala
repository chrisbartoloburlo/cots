/**
 * REST countries API
 * REST countries API
 *
 * The version of the OpenAPI document: v2.0.5
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.restcountries.model


case class CountryV1(
  name: Option[String] = None,
  alpha2Code: Option[String] = None,
  alpha3Code: Option[String] = None,
  callingCodes: Option[Seq[String]] = None,
  capital: Option[String] = None,
  currencies: Option[Seq[String]] = None,
  languages: Option[Seq[String]] = None,
  region: Option[String] = None,
  subregion: Option[String] = None
)
