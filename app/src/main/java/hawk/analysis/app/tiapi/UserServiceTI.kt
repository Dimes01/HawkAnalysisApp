package hawk.analysis.app.tiapi

import hawk.analysis.app.utilities.ErrorResponse
import hawk.analysis.app.utilities.ErrorResponseTI
import hawk.analysis.app.utilities.HawkResponse
import hawk.analysis.restlib.contracts.AccountStatus
import hawk.analysis.restlib.contracts.GetAccountsRequest
import hawk.analysis.restlib.contracts.GetAccountsResponse
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

class UserServiceTI(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserServiceTI::class.java)
    }

    suspend fun getAccounts(authToken: String): HawkResponse<GetAccountsResponse, ErrorResponseTI> {
        log.info("Getting accounts from T-Invest API")
        val requestBody = GetAccountsRequest(AccountStatus.ACCOUNT_STATUS_OPEN)
        val response = client.post("$baseUrl/tinkoff.public.invest.api.contract.v1.UsersService/GetAccounts") {
            bearerAuth(authToken)
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
        println(response.bodyAsText())
        if (response.status.isSuccess()) {
            val body = response.body<GetAccountsResponse>()
            return HawkResponse(response = body, error = null)
        }
        return HawkResponse(response = null, error = response.body<ErrorResponseTI>())
    }
}