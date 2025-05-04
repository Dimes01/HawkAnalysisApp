package hawk.analysis.app.tiapi

import hawk.analysis.app.utilities.authHeader
import hawk.analysis.restlib.dto.GetAccountsRequest
import hawk.analysis.restlib.dto.GetAccountsResponse
import hawk.analysis.restlib.enums.AccountStatus
import hawk.analysis.restlib.models.Account
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UserServiceTI(
    private val client: HttpClient,
    private val baseUrl: String = "https://invest-public-api.tinkoff.ru/rest",
) {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserServiceTI::class.java)
    }

    suspend fun getAccounts(authToken: String): GetAccountsResponse {
        log.info("Getting accounts from T-Invest API")
        val requestBody = GetAccountsRequest(AccountStatus.ACCOUNT_STATUS_OPEN)
        val response = client.post("$baseUrl/tinkoff.public.invest.api.contract.v1.UsersService/GetAccounts") {
            authHeader(authToken)
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
        return response.body()
    }
}