package hawk.analysis.app.services

import android.icu.math.BigDecimal
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.dto.UpdateBenchmarkRequest
import hawk.analysis.app.dto.UpdateRiskFreeRequest
import hawk.analysis.app.utilities.authHeader
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
import kotlinx.datetime.Instant

class AccountService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    val lastUpdatedAt = HashMap<String, Instant>()

    suspend fun getAllByUserId(): List<AccountInfo>? {
        val response = client.get("$baseUrl/api/accounts") {
            authHeader(AuthService.jwt)
        }
        if (response.status.isSuccess()) {
            return response.body<List<AccountInfo>>().also { acc ->
                acc.forEach { lastUpdatedAt[it.id] = it.updatedAt }
            }
        }
        return null
    }

    suspend fun changeRiskFree(accountId: String, riskFree: BigDecimal?): AccountInfo? {
        lastUpdatedAt[accountId]?.also {
            val request = UpdateRiskFreeRequest(accountId, riskFree, it)
            val response = client.patch("$baseUrl/api/accounts/update/risk-free") {
                bearerAuth(AuthService.jwt)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (response.status.isSuccess()) {
                return response.body<AccountInfo>().also { lastUpdatedAt[it.id] = it.updatedAt }
            }
        }
        return null
    }

    suspend fun changeBenchmark(accountId: String, figiBenchmark: String?): AccountInfo? {
        lastUpdatedAt[accountId]?.also {
            val request = UpdateBenchmarkRequest(accountId, figiBenchmark, it)
            val response = client.patch("$baseUrl/api/accounts/update/benchmark") {
                bearerAuth(AuthService.jwt)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (response.status.isSuccess()) {
                return response.body<AccountInfo>().also { lastUpdatedAt[it.id] = it.updatedAt }
            }
        }
        return null
    }
}