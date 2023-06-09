/**
 * GitLab API
 * An OpenAPI definition for the GitLab REST API. Few API resources or endpoints are currently included. The intent is to expand this to match the entire Markdown documentation of the API: <https://docs.gitlab.com/ee/api/>. Contributions are welcome.  When viewing this on gitlab.com, you can test API calls directly from the browser against the `gitlab.com` instance, if you are logged in. The feature uses the current [GitLab session cookie](https://docs.gitlab.com/ee/api/index.html#session-cookie), so each request is made using your account.  Instructions for using this tool can be found in [Interactive API Documentation](openapi_interactive.md). 
 *
 * The version of the OpenAPI document: v4
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package examples.gitlab.api

import examples.gitlab.model.GroupAccessApprove
import examples.gitlab.model.GroupAccessRequest
import examples.gitlab.model.GroupAccessResponse
import examples.gitlab.model.ProjectAccessApprove
import examples.gitlab.model.ProjectAccessRequest
import examples.gitlab.model.ProjectAccessResponse
import examples.gitlab.core.JsonSupport._
import sttp.client3._
import sttp.model.Method

object AccessRequestsApi {

def apply(baseUrl: String = "http://localhost/api") = new AccessRequestsApi(baseUrl)
}

class AccessRequestsApi(baseUrl: String) {

  /**
   * Denies a project access request for the given user
   * 
   * Expected answers:
   *   code 401 :  (Unauthorized operation)
   *   code 200 :  (Successful operation)
   * 
   * Available security schemes:
   *   ApiKeyAuth (apiKey)
   * 
   * @param id The ID or URL-encoded path of the project owned by the authenticated user.
   * @param userId The user ID of the access requester
   */
  def accessRequestProjectsDenyDelete(apiKey: String, id: Int, userId: Int
): Request[Either[ResponseException[String, Exception], Unit], Any] =
    basicRequest
      .method(Method.DELETE, uri"$baseUrl/v4/projects/${id}/access_requests/${userId}")
      .contentType("application/json")
      .header("Private-Token", apiKey)
.response(asJson[Unit])

  /**
   * Approves access for the authenticated user to a group
   * 
   * Expected answers:
   *   code 401 :  (Unauthorized operation)
   *   code 200 : GroupAccessApprove (Successful operation)
   * 
   * Available security schemes:
   *   ApiKeyAuth (apiKey)
   * 
   * @param id The ID or URL-encoded path of the group owned by the authenticated user.
   * @param userId The userID of the access requester
   * @param accessLevel A valid group access level.  0 = no access , 10 = Guest, 20 = Reporter, 30 = Developer, 40 = Maintainer, 50 = Owner.  Default is 30.
   */
  def accessRequestsGroupsApprovePut(apiKey: String, id: Int, userId: Int, accessLevel: Option[Int] = None
): Request[Either[ResponseException[String, Exception], GroupAccessApprove], Any] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/v4/groups/${id}/access_requests/${userId}/approve?access_level=${ accessLevel }")
      .contentType("application/json")
      .header("Private-Token", apiKey)
.response(asJson[GroupAccessApprove])

  /**
   * Denies a group access request for the given user
   * 
   * Expected answers:
   *   code 401 :  (Unauthorized operation)
   *   code 200 :  (Successful operation)
   * 
   * Available security schemes:
   *   ApiKeyAuth (apiKey)
   * 
   * @param id The ID or URL-encoded path of the group owned by the authenticated user.
   * @param userId The userID of the access requester
   */
  def accessRequestsGroupsDenyDelete(apiKey: String, id: Int, userId: Int
): Request[Either[ResponseException[String, Exception], Unit], Any] =
    basicRequest
      .method(Method.DELETE, uri"$baseUrl/v4/groups/${id}/access_requests/${userId}")
      .contentType("application/json")
      .header("Private-Token", apiKey)
.response(asJson[Unit])

  /**
   * List access requests for a group
   * 
   * Expected answers:
   *   code 401 :  (Unauthorized operation)
   *   code 200 : GroupAccessResponse (Successful operation)
   * 
   * Available security schemes:
   *   ApiKeyAuth (apiKey)
   * 
   * @param id The ID or URL-encoded path of the group owned by the authenticated user.
   */
  def accessRequestsGroupsGet(apiKey: String, id: Int
): Request[Either[ResponseException[String, Exception], GroupAccessResponse], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/v4/groups/${id}/access_requests")
      .contentType("application/json")
      .header("Private-Token", apiKey)
.response(asJson[GroupAccessResponse])

  /**
   * Requests access for the authenticated user to a group
   * 
   * Expected answers:
   *   code 401 :  (Unauthorized operation)
   *   code 200 : GroupAccessRequest (Successful operation)
   * 
   * Available security schemes:
   *   ApiKeyAuth (apiKey)
   * 
   * @param id The ID or URL-encoded path of the group owned by the authenticated user.
   */
  def accessRequestsGroupsPost(apiKey: String, id: Int
): Request[Either[ResponseException[String, Exception], GroupAccessRequest], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/v4/groups/${id}/access_requests")
      .contentType("application/json")
      .header("Private-Token", apiKey)
.response(asJson[GroupAccessRequest])

  /**
   * Approves access for the authenticated user to a project
   * 
   * Expected answers:
   *   code 401 :  (Unauthorized operation)
   *   code 200 : ProjectAccessApprove (Successful operation)
   * 
   * Available security schemes:
   *   ApiKeyAuth (apiKey)
   * 
   * @param id The ID or URL-encoded path of the project owned by the authenticated user.
   * @param userId The userID of the access requester
   * @param accessLevel A valid project access level.  0 = no access , 10 = guest, 20 = reporter, 30 = developer, 40 = Maintainer.  Default is 30.'
   */
  def accessRequestsProjectsApprovePut(apiKey: String, id: Int, userId: Int, accessLevel: Option[Int] = None
): Request[Either[ResponseException[String, Exception], ProjectAccessApprove], Any] =
    basicRequest
      .method(Method.PUT, uri"$baseUrl/v4/projects/${id}/access_requests/${userId}/approve?access_level=${ accessLevel }")
      .contentType("application/json")
      .header("Private-Token", apiKey)
.response(asJson[ProjectAccessApprove])

  /**
   * Lists access requests for a project
   * 
   * Expected answers:
   *   code 401 :  (Unauthorized operation)
   *   code 200 : ProjectAccessResponse (Successful operation)
   * 
   * Available security schemes:
   *   ApiKeyAuth (apiKey)
   * 
   * @param id The ID or URL-encoded path of the project owned by the authenticated user.
   */
  def accessRequestsProjectsGet(apiKey: String, id: Int
): Request[Either[ResponseException[String, Exception], ProjectAccessResponse], Any] =
    basicRequest
      .method(Method.GET, uri"$baseUrl/v4/projects/${id}/access_requests")
      .contentType("application/json")
      .header("Private-Token", apiKey)
.response(asJson[ProjectAccessResponse])

  /**
   * Requests access for the authenticated user to a project
   * 
   * Expected answers:
   *   code 401 :  (Unauthorized operation)
   *   code 200 : ProjectAccessRequest (Successful operation)
   * 
   * Available security schemes:
   *   ApiKeyAuth (apiKey)
   * 
   * @param id The ID or URL-encoded path of the project owned by the authenticated user.
   */
  def accessRequestsProjectsPost(apiKey: String, id: Int
): Request[Either[ResponseException[String, Exception], ProjectAccessRequest], Any] =
    basicRequest
      .method(Method.POST, uri"$baseUrl/v4/projects/${id}/access_requests")
      .contentType("application/json")
      .header("Private-Token", apiKey)
.response(asJson[ProjectAccessRequest])

}
