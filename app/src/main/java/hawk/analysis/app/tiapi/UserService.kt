package hawk.analysis.app.tiapi

import io.grpc.ManagedChannel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.tinkoff.piapi.contract.v1.AccountStatus
import ru.tinkoff.piapi.contract.v1.GetAccountsRequest
import ru.tinkoff.piapi.contract.v1.GetAccountsResponse
import ru.tinvest.piapi.core.InvestApi


class UserService {
    private val api: InvestApi

    constructor(token: String) {
        val channel: ManagedChannel = InvestApi.defaultChannel(token = token, target = "invest-public-api.tinkoff.ru:443")
        api = InvestApi.createApi(channel)
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserService::class.java)
    }

    suspend fun getAccounts(): GetAccountsResponse? {
        log.info("Getting accounts from T-Invest API")
        val  request = GetAccountsRequest.newBuilder().setStatus(AccountStatus.ACCOUNT_STATUS_OPEN).build()
        try {
            val response = api.usersServiceAsync.getAccounts(request)
            return response
        } catch (e: Exception) {
            log.error(e.message)
            return null
        }
    }
}