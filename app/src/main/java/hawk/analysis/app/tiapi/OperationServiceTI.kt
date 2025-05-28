package hawk.analysis.app.tiapi

import hawk.analysis.app.utilities.ErrorResponse
import hawk.analysis.app.utilities.ErrorResponseTI
import hawk.analysis.app.utilities.HawkResponse
import hawk.analysis.restlib.contracts.Currency
import hawk.analysis.restlib.contracts.CurrencyRequest
import hawk.analysis.restlib.contracts.InstrumentResponse
import hawk.analysis.restlib.contracts.OperationRequest
import hawk.analysis.restlib.contracts.OperationResponse
import hawk.analysis.restlib.contracts.PortfolioRequest
import hawk.analysis.restlib.contracts.PortfolioResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
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

    suspend fun getOperations(authToken: String, request: OperationRequest): HawkResponse<OperationResponse, ErrorResponseTI> {
        val response = client.post("$baseUrl/tinkoff.public.invest.api.contract.v1.OperationsService/GetOperations") {
            bearerAuth(authToken)
            contentType()
            setBody(request)
        }
        if (response.status.isSuccess()) {
            val body = response.body<OperationResponse>()
            return HawkResponse(response = body, error = null)
        }
        return HawkResponse(response = null, error = response.body<ErrorResponseTI>())
    }

    suspend fun getPortfolio(
        authToken: String, accountId: String, currency: CurrencyRequest = CurrencyRequest.RUB
    ): HawkResponse<PortfolioResponse, ErrorResponseTI> {
        log.info("Getting portfolio from T-InvestA API")
        val request = PortfolioRequest(accountId, currency)
        val response = client.post("$baseUrl/tinkoff.public.invest.api.contract.v1.OperationsService/GetPortfolio") {
            bearerAuth(authToken)
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        if (response.status.isSuccess()) {
            val body = response.body<PortfolioResponse>()
            return HawkResponse(response = body, error = null)
        }
        return HawkResponse(response = null, error = response.body<ErrorResponseTI>())
    }
}