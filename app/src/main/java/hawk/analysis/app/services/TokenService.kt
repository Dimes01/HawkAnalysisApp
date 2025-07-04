package hawk.analysis.app.services

import hawk.analysis.app.dto.CreateTokenRequest
import hawk.analysis.app.dto.RemoveTokenRequest
import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.app.dto.UpdateTokenRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.datetime.Instant

class TokenService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    var lastUpdatedAt = HashMap<Int, Instant>()
        private set

    suspend fun getAllByUserId(): List<TokenInfo>? {
        val response = client.get("$baseUrl/api/tokens") {
            bearerAuth(AuthService.jwt)
        }
        if (response.status.isSuccess()) {
            val body = response.body<List<TokenInfo>>()
            body.forEach { lastUpdatedAt[it.id] = it.updatedAt }
            return body
        }
        println(response.bodyAsText())
        return null
    }

    suspend fun create(name: String, password: String, authToken: String): Boolean {
        val bodyRequest = CreateTokenRequest(name = name, password = password, authToken = authToken)
        val response = client.post("$baseUrl/api/tokens") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(bodyRequest)
        }
        return response.status.isSuccess().also { println(response.bodyAsText()) }
    }

    suspend fun update(id: Int, name: String, password: String): Boolean {
        lastUpdatedAt[id]?.also {
            val bodyRequest = UpdateTokenRequest(id, name, password, it)
            val response = client.patch("$baseUrl/api/tokens") {
                bearerAuth(AuthService.jwt)
                contentType(ContentType.Application.Json)
                setBody(bodyRequest)
            }
            println(response.bodyAsText())
            return response.status.isSuccess()
        }
        return false
    }

    suspend fun remove(id: Int, password: String): Boolean {
        val bodyRequest = RemoveTokenRequest(id, password)
        val response = client.delete("$baseUrl/api/tokens") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(bodyRequest)
        }
        println(response.bodyAsText())
        return response.status.isSuccess()
    }
}