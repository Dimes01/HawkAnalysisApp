package hawk.analysis.app.tiapi

import hawk.analysis.app.utilities.authHeader
import hawk.analysis.restlib.contracts.CurrencyRequest
import hawk.analysis.restlib.contracts.PortfolioRequest
import hawk.analysis.restlib.contracts.PortfolioResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class OperationServiceTI(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(OperationServiceTI::class.java)
    }

    suspend fun getPortfolio(
        authToken: String, accountId: String, currency: CurrencyRequest = CurrencyRequest.RUB
    ): PortfolioResponse? {
        log.info("Getting portfolio from T-InvestA API")
        val request = PortfolioRequest(accountId, currency)
        val response = client.post("$baseUrl/rest/tinkoff.public.invest.api.contract.v1.OperationsService/GetPortfolio") {
            authHeader(authToken)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return if (response.status.isSuccess()) response.body() else null
    }
}