package hawk.analysis.app.tiapi

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ru.tinkoff.piapi.contract.v1.Account
import ru.tinkoff.piapi.contract.v1.AccountStatus
import ru.tinkoff.piapi.core.InvestApi

class UserService(token: String) {
    private val api: InvestApi = InvestApi.createReadonly(token)

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserService::class.java)
    }

    fun getAccounts(): List<Account>? {
        log.info("Getting accounts from T-Invest API")
        try {
            val future = api.userService.getAccounts(AccountStatus.ACCOUNT_STATUS_OPEN)
            return future.get()
        } catch (e: Exception) {
            log.error(e.message)
            return null
        }
    }
}