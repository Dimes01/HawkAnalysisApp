package hawk.analysis.app.services

import hawk.analysis.app.dto.Analyse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

class AnalyseService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    suspend fun getLast(accountId: String): List<Analyse>? {
        val response = client.get("$baseUrl/api/analysis/$accountId/latest") { bearerAuth(AuthService.jwt) }
        if (response.status.isSuccess()) return response.body()
        println(response.bodyAsText())
        return null
    }
}