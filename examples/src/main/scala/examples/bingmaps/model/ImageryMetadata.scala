/**
 * Bing Maps Api
 * No description provided (generated by Openapi Generator https://github.com/openapitools/openapi-generator)
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.bingmaps.model


case class ImageryMetadata(
  statusCode: Option[Int] = None,
  authenticationResultCode: Option[String] = None,
  resourceSets: Option[Seq[ImageryResourceSets]] = None
)
