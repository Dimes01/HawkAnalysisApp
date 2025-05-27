package hawk.analysis.app.services

import android.icu.math.BigDecimal
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.dto.UpdateBenchmarkRequest
import hawk.analysis.app.dto.UpdateRiskFreeRequest
import hawk.analysis.app.utilities.ErrorResponse
import hawk.analysis.app.utilities.HawkResponse
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

    suspend fun getAllByUserId(): HawkResponse<List<AccountInfo>> {
        val response = client.get("$baseUrl/api/accounts") {
            bearerAuth(AuthService.jwt)
        }
        if (response.status.isSuccess()) {
            val body = response.body<List<AccountInfo>>()
            body.forEach { lastUpdatedAt[it.id] = it.updatedAt }
            return HawkResponse(response = body, error = null)
        }
        val error = response.body<ErrorResponse>()
        return HawkResponse(response = null, error = error)
    }

    suspend fun changeRiskFree(accountId: String, riskFree: BigDecimal?): HawkResponse<AccountInfo> {
        val lastUpd = lastUpdatedAt[accountId]
        return if (lastUpd != null) {
            val request = UpdateRiskFreeRequest(accountId, riskFree, lastUpd)
            val response = client.patch("$baseUrl/api/accounts/update/risk-free") {
                bearerAuth(AuthService.jwt)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (response.status.isSuccess()) {
                val body = response.body<AccountInfo>()
                lastUpdatedAt[body.id] = body.updatedAt
                HawkResponse(response = body, error = null)
            } else HawkResponse(response = null, error = response.body<ErrorResponse>())
        } else HawkResponse(response = null, error = null)
    }

    suspend fun changeBenchmark(accountId: String, figiBenchmark: String?): HawkResponse<AccountInfo> {
        val lastUpd = lastUpdatedAt[accountId]
        return if (lastUpd != null) {
            val request = UpdateBenchmarkRequest(accountId, figiBenchmark, lastUpd)
            val response = client.patch("$baseUrl/api/accounts/update/benchmark") {
                bearerAuth(AuthService.jwt)
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            if (response.status.isSuccess()) {
                val body = response.body<AccountInfo>()
                lastUpdatedAt[body.id] = body.updatedAt
                HawkResponse(response = body, error = null)
            } else HawkResponse(response = null, error = response.body<ErrorResponse>())
        } else HawkResponse(response = null, error = null)
    }
}