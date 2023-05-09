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
package examples.usersregistry.model


case class Role(
  active: Option[Boolean] = None,
  id: Option[Long] = None,
  name: Option[String] = None,
  initials: Option[String] = None,
  description: Option[String] = None
)
