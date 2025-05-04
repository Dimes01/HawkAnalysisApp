package hawk.analysis.app.tiapi

import org.slf4j.Logger
import org.slf4j.LoggerFactory


class UserService(token: String) {
//    private val api: InvestApi = InvestApi.createReadonly(token)

    companion object {
        private val log: Logger = LoggerFactory.getLogger(UserService::class.java)
    }

    fun getAccounts(): List<Any>? {
        log.info("Getting accounts from T-Invest API")
        return null
    }
}