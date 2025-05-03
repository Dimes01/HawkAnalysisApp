package hawk.analysis.app.services

import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.utilities.authHeader
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class AccountService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    suspend fun getAllByUserId(jwt: String): List<AccountInfo>? {
        val response = client.get("$baseUrl/api/accounts") {
            authHeader(jwt)
        }
        if (response.status.isSuccess()) return response.body<List<AccountInfo>>()
        println("Could not get accounts from API. Response: ${response.bodyAsText()}")
        return null
    }
}