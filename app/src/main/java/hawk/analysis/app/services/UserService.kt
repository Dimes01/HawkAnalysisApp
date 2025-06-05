package hawk.analysis.app.services

import hawk.analysis.app.dto.UpdateEmailRequest
import hawk.analysis.app.dto.UpdatePasswordRequest
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.app.utilities.ErrorResponse
import hawk.analysis.app.utilities.NotSuccessfulResponseException
import hawk.analysis.app.utilities.tryParseError
import hawk.analysis.app.utilities.withTimeOut
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
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

    suspend fun getById(): UserInfo {
        val response = withTimeOut { client.get("$baseUrl/api/users") { bearerAuth(AuthService.jwt) } }
        if (response.status.isSuccess()) {
            val body = response.body<UserInfo>()
            lastUpdatedAt = body.updatedAt
            return body
        }
        val error = tryParseError { response.body<ErrorResponse>() }
        throw NotSuccessfulResponseException(response, error)
    }

    suspend fun updateEmail(newEmail: String, password: String): UserInfo {
        val request = UpdateEmailRequest(lastUpdatedAt = lastUpdatedAt, password = password, newEmail = newEmail)
        val response = withTimeOut { client.patch("$baseUrl/api/users/update/email") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(request)
        } }
        if (response.status.isSuccess()) {
            val body = response.body<UserInfo>()
            lastUpdatedAt = body.updatedAt
            return body
        }
        val error = tryParseError { response.body<ErrorResponse>() }
        throw NotSuccessfulResponseException(response, error)
    }

    suspend fun updatePassword(oldPassword: String, newPassword: String): UserInfo {
        val request = UpdatePasswordRequest(lastUpdatedAt, oldPassword, newPassword)
        val response = withTimeOut { client.patch("$baseUrl/api/users/update/password") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(request)
        } }
        if (response.status.isSuccess()) {
            val body = response.body<UserInfo>()
            lastUpdatedAt = body.updatedAt
            return body
        }
        val error = tryParseError { response.body<ErrorResponse>() }
        throw NotSuccessfulResponseException(response, error)
    }
}