/**
 * OpenAPI Petstore
 * This is a sample server Petstore server. For this sample, you can use the api key `special-key` to test the authorization filters. For OAuth2 flow, you may use `user` as both username and password when asked to login.
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.petstore.model


  /**
   * a Pet
   * A pet for sale in the pet store
   */
case class Pet(
  id: Long,
  category: Category,
  name: String,
  photoUrls: Seq[String],
  tags: Seq[Tag],
  /* pet status in the store */
  status: PetEnums.Status
)

object PetEnums {

  type Status = Status.Value
  object Status extends Enumeration {
    val Available = Value("available")
    val Pending = Value("pending")
    val Sold = Value("sold")
  }

}
