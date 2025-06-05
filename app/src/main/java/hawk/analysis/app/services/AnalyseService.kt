package hawk.analysis.app.services

import hawk.analysis.app.dto.AccountAnalyse
import hawk.analysis.app.dto.AnalyseRequest
import hawk.analysis.app.dto.AssetAnalyse
import hawk.analysis.app.utilities.ErrorResponse
import hawk.analysis.app.utilities.NotSuccessfulResponseException
import hawk.analysis.app.utilities.tryParseError
import hawk.analysis.app.utilities.withTimeOut
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess

class AnalyseService(
    private val baseUrl: String,
    private val client: HttpClient
) {
    suspend fun getAssetLast(accountId: String): List<AssetAnalyse> {
        val response = withTimeOut { client.post("$baseUrl/api/analysis/assets/latest") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(AnalyseRequest(accountId))
        } }
        if (response.status.isSuccess()) return response.body()
        val error = tryParseError { response.body<ErrorResponse>() }
        throw NotSuccessfulResponseException(response, error)
    }

    suspend fun getAccountLast(accountId: String): List<AccountAnalyse> {
        val response = withTimeOut { client.post("$baseUrl/api/analysis/accounts/latest") {
            bearerAuth(AuthService.jwt)
            contentType(ContentType.Application.Json)
            setBody(AnalyseRequest(accountId))
        } }
        if (response.status.isSuccess()) return response.body()
        val error = tryParseError { response.body<ErrorResponse>() }
        throw NotSuccessfulResponseException(response, error)
    }
}