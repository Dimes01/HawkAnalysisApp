package hawk.analysis.app.viewmodels

import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.restlib.contracts.Account

class SharedViewModel {
    private val _accountsToToken: MutableList<Pair<Account, TokenInfo>> = mutableListOf()
    val accountsToToken: List<Pair<Account, TokenInfo>> = _accountsToToken

    fun update(collection: Collection<Pair<Account, TokenInfo>>) {
        _accountsToToken.clear()
        _accountsToToken.addAll(collection)
    }

    fun getTokenByAccountId(accountId: String): TokenInfo? = _accountsToToken.firstOrNull {
        it.first.id == accountId
    }?.second
}