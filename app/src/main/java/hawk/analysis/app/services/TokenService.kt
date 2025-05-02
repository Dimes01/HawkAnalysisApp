package hawk.analysis.app.services

import hawk.analysis.app.dto.CreateTokenRequest
import hawk.analysis.app.dto.RemoveTokenRequest
import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.app.dto.UpdateTokenRequest
import hawk.analysis.app.utilities.authHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.datetime.Instant

class TokenService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    suspend fun getAllByUserId(jwt: String): List<TokenInfo> {
        val response = client.get("$baseUrl/api/tokens") {
            authHeader(jwt)
        }
        return if (response.status.isSuccess()) response.body() else emptyList()
    }

    suspend fun create(jwt: String, name: String, password: String, authToken: String): Boolean {
        val bodyRequest = CreateTokenRequest(name, password, authToken)
        val response = client.post("$baseUrl/api/tokens") {
            authHeader(jwt)
            contentType(ContentType.Application.Json)
            setBody(bodyRequest)
        }
        return response.status.isSuccess()
    }

    suspend fun update(jwt: String, id: Int, name: String, password: String, lastUpdatedAt: Instant): Boolean {
        val bodyRequest = UpdateTokenRequest(id, name, password, lastUpdatedAt)
        val response = client.patch("$baseUrl/api/tokens") {
            authHeader(jwt)
            contentType(ContentType.Application.Json)
            setBody(bodyRequest)
        }
        return response.status.isSuccess()
    }

    suspend fun remove(jwt: String, id: Int, password: String): Boolean {
        val bodyRequest = RemoveTokenRequest(id, password)
        val response = client.delete("$baseUrl/api/tokens") {
            authHeader(jwt)
            contentType(ContentType.Application.Json)
            setBody(bodyRequest)
        }
        return response.status.isSuccess()
    }
}