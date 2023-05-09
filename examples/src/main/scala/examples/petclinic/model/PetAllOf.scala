/**
 * Spring PetClinic
 * Spring PetClinic Sample Application.
 *
 * The version of the OpenAPI document: 1.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.petclinic.model


case class PetAllOf(
  /* The ID of the pet. */
  id: Int,
  /* The ID of the pet's owner. */
  ownerId: Option[Int] = None,
  /* Vet visit bookings for this pet. */
  visits: Seq[Visit]
)
