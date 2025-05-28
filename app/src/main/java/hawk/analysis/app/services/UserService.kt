package hawk.analysis.app.services

import hawk.analysis.app.dto.UpdateEmailRequest
import hawk.analysis.app.dto.UpdatePasswordRequest
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.app.utilities.ErrorResponse
import hawk.analysis.app.utilities.HawkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class UserService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    var lastUpdatedAt: Instant = Clock.System.now()
        private set

    suspend fun getById(): HawkResponse<UserInfo, ErrorResponse> {
        val response = client.get("$baseUrl/api/users") { bearerAuth(AuthService.jwt) }
        if (response.status.isSuccess()) {
            val body = response.body<UserInfo>()
            lastUpdatedAt = body.updatedAt
            return HawkResponse(response = body, error = null)
        }
        val error = response.body<ErrorResponse>()
        return HawkResponse(response = null, error = error)
    }

    suspend fun updateEmail(newEmail: String, password: String): HawkResponse<UserInfo, ErrorResponse> {
        val request = UpdateEmailRequest(lastUpdatedAt = lastUpdatedAt, password = password, newEmail = newEmail)
        val response = client.patch("$baseUrl/api/users/update/email") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) {
            val body = response.body<UserInfo>()
            lastUpdatedAt = body.updatedAt
            return HawkResponse(response = body, error = null)
        }
        val error = response.body<ErrorResponse>()
        return HawkResponse(response = null, error = error)
    }

    suspend fun updatePassword(oldPassword: String, newPassword: String): HawkResponse<UserInfo, ErrorResponse> {
        val request = UpdatePasswordRequest(lastUpdatedAt, oldPassword, newPassword)
        val response = client.patch("$baseUrl/api/users/update/password") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) {
            val body = response.body<UserInfo>()
            lastUpdatedAt = body.updatedAt
            return HawkResponse(response = body, error = null)
        }
        val error = response.body<ErrorResponse>()
        return HawkResponse(response = null, error = error)
    }
}