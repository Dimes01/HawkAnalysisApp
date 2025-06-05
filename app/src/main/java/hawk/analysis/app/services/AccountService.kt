package hawk.analysis.app.services

import android.icu.math.BigDecimal
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.dto.UpdateBenchmarkRequest
import hawk.analysis.app.dto.UpdateRiskFreeRequest
import hawk.analysis.app.utilities.ErrorResponse
import hawk.analysis.app.utilities.NotSuccessfulRequestException
import hawk.analysis.app.utilities.NotSuccessfulResponseException
import hawk.analysis.app.utilities.tryParseError
import hawk.analysis.app.utilities.withTimeOut
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.datetime.Instant

class AccountService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    companion object {
        private val notInfoLastUpdateException = NotSuccessfulRequestException("Нет информации о времени последнего обновления")
    }
    val lastUpdatedAt = HashMap<String, Instant>()

    suspend fun getAllByUserId(): List<AccountInfo> {
        val response = withTimeOut { client.get("$baseUrl/api/accounts") {
            bearerAuth(AuthService.jwt)
        } }
        if (response.status.isSuccess()) return response.body<List<AccountInfo>>().also { acc ->
            acc.forEach { lastUpdatedAt[it.id] = it.updatedAt }
        }
        val error = tryParseError { response.body<ErrorResponse>() }
        throw NotSuccessfulResponseException(response, error)
    }

    suspend fun changeRiskFreeResponse(request: UpdateRiskFreeRequest): HttpResponse =
        client.patch("$baseUrl/api/accounts/update/risk-free") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(request)
        }

    suspend fun changeRiskFree(accountId: String, riskFree: BigDecimal?): AccountInfo {
        var lastUpd = lastUpdatedAt[accountId]
        if (lastUpd == null) throw notInfoLastUpdateException
        var request = UpdateRiskFreeRequest(accountId, riskFree, lastUpd)
        var response = withTimeOut { changeRiskFreeResponse(request) }

        if (response.status.value == 409) getAllByUserId()

        lastUpd = lastUpdatedAt[accountId]
        if (lastUpd == null) throw notInfoLastUpdateException
        request = UpdateRiskFreeRequest(accountId, riskFree, lastUpd)
        response = withTimeOut { changeRiskFreeResponse(request) }

        if (response.status.isSuccess()) {
            return response.body<AccountInfo>().also { lastUpdatedAt[it.id] = it.updatedAt }
        }
        val error = tryParseError { response.body<ErrorResponse>() }
        throw NotSuccessfulResponseException(response, error)
    }

    suspend fun changeBenchmarkResponse(request: UpdateBenchmarkRequest): HttpResponse =
        client.patch("$baseUrl/api/accounts/update/benchmark") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(request)
        }

    suspend fun changeBenchmark(accountId: String, figiBenchmark: String?): AccountInfo {
        var lastUpd = lastUpdatedAt[accountId]
        if (lastUpd == null) throw notInfoLastUpdateException
        var request = UpdateBenchmarkRequest(accountId = accountId, figiBenchmark = figiBenchmark, lastUpdatedAt = lastUpd)
        var response = withTimeOut { changeBenchmarkResponse(request) }

        if (response.status.value == 409) getAllByUserId()

        lastUpd = lastUpdatedAt[accountId]
        if (lastUpd == null) throw notInfoLastUpdateException
        response = withTimeOut { changeBenchmarkResponse(request) }
        request = UpdateBenchmarkRequest(accountId = accountId, figiBenchmark = figiBenchmark, lastUpdatedAt = lastUpd)

        if (response.status.isSuccess()) {
            return response.body<AccountInfo>().also { lastUpdatedAt[it.id] = it.updatedAt }
        }
        val error = tryParseError { response.body<ErrorResponse>() }
        throw NotSuccessfulResponseException(response, error)
    }
}