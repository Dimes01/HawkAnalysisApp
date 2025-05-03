package hawk.analysis.app.services

import hawk.analysis.app.dto.LoginRequest
import hawk.analysis.app.dto.LoginResponse
import hawk.analysis.app.dto.RegisterRequest
import hawk.analysis.app.dto.RegisterResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AuthService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    companion object {
        var jwt: String = ""
            private set
    }

    suspend fun login(email: String, password: String): LoginResponse? {
        val requestBody = LoginRequest(email = email, password = password)
        val response = client.post("$baseUrl/api/users/sign-in") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
        if (response.status.isSuccess()) {
            val responseBody = response.body<LoginResponse>()
            jwt = responseBody.jwtToken
            return responseBody
        }
        return null
    }

    suspend fun register(name: String, email: String, password: String): RegisterResponse? {
        val requestBody = RegisterRequest(name = name, email = email, password = password)
        val response = client.post("$baseUrl/api/users/sign-up") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
        if (response.status.isSuccess()) {
            val responseBody = response.body<RegisterResponse>()
            jwt = responseBody.jwtToken
            return responseBody
        }
        return null
    }
}