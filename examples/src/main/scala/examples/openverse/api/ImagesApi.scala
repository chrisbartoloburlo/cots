/**
 * Openverse API consumer docs
 * This documentation is focused towards consumers who are using the Openverse API. The developer documentation for the Openverse API can be found [here]().   # Introduction  The Openverse API is a system that allows programmatic access to public domain digital media. It is our ambition to index and catalog billions of openly-licensed works, including articles, songs, videos, photographs, paintings, and more. Using this API, developers will be able to access the digital commons in their own applications.  Please note that there is a rate limit of 100 requests per day and 5 requests per hour rate limit in place for anonymous users. This is fine for introducing yourself to the API, but we strongly recommend that you obtain an API key as soon as possible. Authorized clients have a higher rate limit of 10000 requests per day and 100 requests per minute. Additionally, Openverse can give your key an even higher limit that fits your application's needs. See the [Register and Authenticate section](#section/Register-and-Authenticate) for instructions on obtaining an API key.  # Register and Authenticate  ## Register for a key Before using the Openverse API, you need to register access via OAuth2. This can be done using the `/v1/auth_tokens/register` endpoint.  Example on how to register for a key: ```bash $ curl \\   -X POST \\   -H \"Content-Type: application/json\" \\   -d '{\"name\": \"My amazing project\", \"description\": \"To access Openverse API\", \"email\": \"user@example.com\"}' \\   \"https://api.openverse.engineering/v1/auth_tokens/register\" ``` If your request is successful, you will get a `client_id` and `client_secret`.  Example of successful request: ```json {     \"client_secret\" : \"YhVjvIBc7TuRJSvO2wIi344ez5SEreXLksV7GjalLiKDpxfbiM8qfUb5sNvcwFOhBUVzGNdzmmHvfyt6yU3aGrN6TAbMW8EOkRMOwhyXkN1iDetmzMMcxLVELf00BR2e\",     \"client_id\" : \"pm8GMaIXIhkjQ4iDfXLOvVUUcIKGYRnMlZYApbda\",     \"name\" : \"My amazing project\" } ```  ## Authenticate In order to use the Openverse API endpoints, you need to include access token in the header. This can be done by exchanging your client credentials for a token using the `/v1/auth_tokens/token/` endpoint.  Example on how to authenticate using OAuth2: ```bash $ curl \\   -X POST \\   -d \"client_id=pm8GMaIXIhkjQ4iDfXLOvVUUcIKGYRnMlZYApbda&client_secret=YhVjvIBc7TuRJSvO2wIi344ez5SEreXLksV7GjalLiKDpxfbiM8qfUb5sNvcwFOhBUVzGNdzmmHvfyt6yU3aGrN6TAbMW8EOkRMOwhyXkN1iDetmzMMcxLVELf00BR2e&grant_type=client_credentials\" \\   \"https://api.openverse.engineering/v1/auth_tokens/token/\" ``` If your request is successful, you will get an access token.  Example of successful request: ```json  {     \"access_token\" : \"DLBYIcfnKfolaXKcmMC8RIDCavc2hW\",     \"scope\" : \"read write groups\",     \"expires_in\" : 36000,     \"token_type\" : \"Bearer\"  } ```  Check your email for a verification link. After you have followed the link, your API key will be activated.  ## Using Access Token Include the `access_token` in the authorization header to use your key in your future API requests.  Example on how to make an authenticated request: ```bash $ curl \\   -H \"Authorization: Bearer DLBYIcfnKfolaXKcmMC8RIDCavc2hW\" \\   \"https://api.openverse.engineering/v1/images?q=test\" ```  > **NOTE:** Your token will be throttled like an anonymous user until the email > address has been verified.  # Glossary  | Term              | Definition | |-------------------|---| | API               | an abbreviation for Application Programming Interface | | OAuth2            | an authorization framework that enables a third party application to get access to an HTTP service | | access token      | a private string that authorizes an application to make API requests | | client ID         | a publicly exposed string used by Openverse API to identify the application | | client secret     | a private string that authenticates the identity of the application to the Openverse API | | CC                | an abbreviation for Creative Commons | | copyright         | a type of intellectual property that gives the owner an exclusive right to reproduce, publish, sell or distribute content | | mature content    | any content that requires the audience to be 18 and older | | sensitive content | any content that depicts graphic violence, adult content, and hostility or malice against others based on their race, religion, disability, sexual orientation, ethnicity and national origin |  # Contribute  We love pull requests! If you’re interested in [contributing on Github](https://github.com/WordPress/openverse-api/blob/main/CONTRIBUTING.md), here’s a todo list to get started.  - Read up about [Django REST Framework](https://www.django-rest-framework.org/),   which is the framework used to build Openverse API - Read up about [drf-yasg](https://drf-yasg.readthedocs.io/en/stable/), which is   a tool used to generate real Swagger/OpenAPI 2.0 specifications - Read up about Documentation Guidelines, which provides guidelines on how to   contribute to documentation, documentation styles and cheat sheet for drf-yasg - Run the server locally by following this   [link](https://github.com/wordpress/openverse-api#running-the-server-locally) - Update documentation or codebase - Make sure the updates passed the automated tests in this   [file](https://github.com/WordPress/openverse-api/blob/master/.github/workflows/integration-tests.yml) - Commit and push - Create pull request 
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: openverse@wordpress.org
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.openverse.api

import examples.openverse.model.Image
import examples.openverse.model.ImageReportRequest
import examples.openverse.model.ImageSearch
import examples.openverse.model.InputError
import examples.openverse.model.MediaThumbnailRequest
import examples.openverse.model.NotFoundError
import examples.openverse.model.Oembed
import examples.openverse.model.Provider
import examples.openverse.core.JsonSupport._
import sttp.client3._
import sttp.model.Method

object ImagesApi {

def apply(baseUrl: String = "http://localhost:50280/v1") = new ImagesApi(baseUrl)
}

class ImagesApi(baseUrl: String) {

  /**
   *  image_detail is an API endpoint to get the details of a specified image ID.  By using this endpoint, you can image details such as `id`, `title`, `foreign_landing_url`, `url`, `creator`, `creator_url`, `license`, `license_version`, `license_url`, `provider`, `source`, `category`, `filesize`, `filetype`, `tags`, `attribution`, `fields_matched`, `mature`, `height`, `width`, `thumbnail`, `detail_url` and `related_url`.   You can refer to the cURL request samples for examples on how to consume this endpoint. 
   * 
   * Expected answers:
   *   code 200 : Image (OK)
   *   code 404 : NotFoundError (Not Found)
   * 
   * @param identifier 
   * @param format 
   */
  def imageDetail(identifier: String, format: String
): Request[Either[ResponseException[String, Exception], Image], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/images/${identifier}/?format=${ format }")
      .contentType("application/json")
      .response(asJson[Image])

  /**
   *  oembed_list is an API endpoint to retrieve embedded content from a specified image URL.  By using this endpoint, you can retrieve embedded content such as `version`, `type`, `width`, `height`, `title`, `author_name`, `author_url` and `license_url`.   You can refer to the cURL request samples for examples on how to consume this endpoint. 
   * 
   * Expected answers:
   *   code 200 : Oembed (OK)
   *   code 404 : NotFoundError (Not Found)
   * 
   * @param format 
   * @param url The URL of an image.
   */
  def imageOembed(format: String, url: String
): Request[Either[ResponseException[String, Exception], Oembed], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/images/oembed/?format=${ format }&url=${ url }")
      .contentType("application/json")
      .response(asJson[Oembed])

  /**
   *  recommendations_images_read is an API endpoint to get related images for a specified image ID.  By using this endpoint, you can get the details of related images such as `id`, `title`, `foreign_landing_url`, `url`, `creator`, `creator_url`, `license`, `license_version`, `license_url`, `provider`, `source`, `category`, `filesize`, `filetype`, `tags`, `attribution`, `fields_matched`, `mature`, `height`, `width`, `thumbnail`, `detail_url` and `related_url`.   You can refer to the cURL request samples for examples on how to consume this endpoint. 
   * 
   * Expected answers:
   *   code 200 : Image (OK)
   *   code 404 : NotFoundError (Not Found)
   * 
   * @param identifier 
   * @param format 
   */
  def imageRelated(identifier: String, format: String
): Request[Either[ResponseException[String, Exception], Image], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/images/${identifier}/related/?format=${ format }")
      .contentType("application/json")
      .response(asJson[Image])

  /**
   *  images_report_create is an API endpoint to report an issue about a specified image ID to Openverse.  By using this endpoint, you can report an image if it infringes copyright, contains mature or sensitive content and others.   By using this endpoint, you can report a file if it infringes copyright, contains mature or sensitive content and others.   You can refer to the cURL request samples for examples on how to consume this endpoint. 
   * 
   * Expected answers:
   *   code 201 : ImageReportRequest (OK)
   * 
   * @param identifier 
   * @param format 
   * @param data 
   */
  def imageReport(identifier: String, format: String, data: ImageReportRequest
): Request[Either[ResponseException[String, Exception], ImageReportRequest], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/images/${identifier}/report/?format=${ format }")
      .contentType("application/json")
      .body(data)
      .response(asJson[ImageReportRequest])

  /**
   *  image_search is an API endpoint to search images using a query string.  By using this endpoint, you can obtain search results based on specified query and optionally filter results by `q`, `license`, `license_type`, `creator`, `tags`, `title`, `filter_dead`, `extension`, `mature`, `qa`, `page_size`, `source`, `excluded_source`, `category`, `aspect_ratio` and `size`.   Results are ranked in order of relevance and paginated on the basis of the `page` param. The `page_size` param controls the total number of pages.  Although there may be millions of relevant records, only the most relevant several thousand records can be viewed. This is by design: the search endpoint should be used to find the top 10,000 most relevant results, not for exhaustive search or bulk download of every barely relevant result. As such, the caller should not try to access pages beyond `page_count`, or else the server will reject the query.  For more precise results, you can go to the [Openverse Syntax Guide](https://search.creativecommons.org/search-help) for information about creating queries and [Apache Lucene Syntax Guide](https://lucene.apache.org/core/2_9_4/queryparsersyntax.html) for information on structuring advanced searches.   You can refer to the cURL request samples for examples on how to consume this endpoint. 
   * 
   * Expected answers:
   *   code 200 : ImageSearch (OK)
   *   code 400 : InputError (Bad Request)
   * 
   * @param format 
   * @param `q` A query string that should not exceed 200 characters in length
   * @param pageSize Number of results to return per page.
   * @param source A comma separated list of data sources; available data sources include: `none_found`.
   * @param excludedSource A comma separated list of data sources; available data sources include: `none_found`.
   * @param license A comma separated list of licenses; available licenses include: `by`, `by-nc`, `by-nc-nd`, `by-nc-sa`, `by-nd`, `by-sa`, `cc0`, `nc-sampling+`, `pdm`, and `sampling+`.
   * @param licenseType A comma separated list of license types; available license types include: `all`, `all-cc`, `commercial`, and `modification`.
   * @param creator Search by creator only. Cannot be used with `q`.
   * @param tags Search by tag only. Cannot be used with `q`.
   * @param title Search by title only. Cannot be used with `q`.
   * @param filterDead Control whether 404 links are filtered out.
   * @param extension A comma separated list of desired file extensions.
   * @param mature Whether to include content for mature audiences.
   * @param qa If enabled, searches are performed against the quality assurance index instead of production.
   * @param category A comma separated list of categories; available categories include: `digitized_artwork`, `illustration`, and `photograph`.
   * @param aspectRatio A comma separated list of aspect ratios; available aspect ratios include: `square`, `tall`, and `wide`.
   * @param size A comma separated list of image sizes; available image sizes include: `large`, `medium`, and `small`.
   */
  def imageSearch(format: String, `q`: Option[String] = None, pageSize: Option[Int] = None, source: Option[String] = None, excludedSource: Option[String] = None, license: Option[String] = None, licenseType: Option[String] = None, creator: Option[String] = None, tags: Option[String] = None, title: Option[String] = None, filterDead: Option[Boolean] = None, extension: Option[String] = None, mature: Option[Boolean] = None, qa: Option[Boolean] = None, category: Option[String] = None, aspectRatio: Option[String] = None, size: Option[String] = None
): Request[Either[ResponseException[String, Exception], ImageSearch], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/images/?format=${ format }&q=${ `q`.getOrElse(None) }&page_size=${ pageSize.getOrElse(None) }&source=${ source.getOrElse(None) }&excluded_source=${ excludedSource.getOrElse(None) }&license=${ license.getOrElse(None) }&license_type=${ licenseType.getOrElse(None) }&creator=${ creator.getOrElse(None) }&tags=${ tags.getOrElse(None) }&title=${ title.getOrElse(None) }&filter_dead=${ filterDead.getOrElse(None) }&extension=${ extension.getOrElse(None) }&mature=${ mature.getOrElse(None) }&qa=${ qa.getOrElse(None) }&category=${ category.getOrElse(None) }&aspect_ratio=${ aspectRatio.getOrElse(None) }&size=${ size.getOrElse(None) }")
      .contentType("application/json")
      .response(asJson[ImageSearch])

  /**
   *  image_stats is an API endpoint to get a list of all content providers and their respective number of images in the Openverse catalog.   You can use this endpoint to get details about content providers such as `source_name`, `display_name`, and `source_url` along with a count of the number of individual items indexed from them.   You can refer to the cURL request samples for examples on how to consume this endpoint. 
   * 
   * Expected answers:
   *   code 200 : Seq[Provider] (OK)
   * 
   * @param format 
   */
  def imageStats(format: String
): Request[Either[ResponseException[String, Exception], Seq[Provider]], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/images/stats/?format=${ format }")
      .contentType("application/json")
      .response(asJson[Seq[Provider]])

  /**
   *  thumbnail is an API endpoint to retrieve the scaled down and compressed thumbnail of an image.   You can refer to the cURL request samples for examples on how to consume this endpoint. 
   * 
   * Expected answers:
   *   code 200 : MediaThumbnailRequest ()
   * 
   * @param identifier 
   * @param format 
   * @param fullSize whether to render the actual image and not a thumbnail version
   * @param compressed whether to compress the output image to reduce file size,defaults to opposite of `full_size`
   */
  def imageThumbnail(identifier: String, format: String, fullSize: Option[Boolean] = None, compressed: Option[Boolean] = None
): Request[Either[ResponseException[String, Exception], MediaThumbnailRequest], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/images/${identifier}/thumb/?format=${ format }&full_size=${ fullSize.getOrElse(None) }&compressed=${ compressed.getOrElse(None) }")
      .contentType("application/json")
      .response(asJson[MediaThumbnailRequest])

}
