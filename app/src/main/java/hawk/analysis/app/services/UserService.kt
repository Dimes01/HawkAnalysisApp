package hawk.analysis.app.services

import hawk.analysis.app.dto.UpdateEmailRequest
import hawk.analysis.app.dto.UpdatePasswordRequest
import hawk.analysis.app.dto.UserInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class UserService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    suspend fun getById(jwt: String): UserInfo? {
        val response = client.get("$baseUrl/api/users") { bearerAuth(jwt) }
        return if (response.status.isSuccess()) return response.body()
        else null
    }

    suspend fun updateEmail(jwt: String, request: UpdateEmailRequest): UserInfo? {
        val response = client.patch("$baseUrl/api/users/update/email") {
            bearerAuth(jwt)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return if (response.status.isSuccess()) return response.body()
        else null
    }

    suspend fun updatePassword(jwt: String, request: UpdatePasswordRequest): UserInfo? {
        val response = client.patch("$baseUrl/api/users/update/password") {
            bearerAuth(jwt)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return if (response.status.isSuccess()) return response.body()
        else null
    }
}