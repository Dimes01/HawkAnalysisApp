package hawk.analysis.app.screens

import android.icu.math.BigDecimal
import hawk.analysis.app.dto.AccountInfo
import hawk.analysis.app.dto.TokenInfo
import hawk.analysis.app.dto.UserInfo
import hawk.analysis.restlib.contracts.Currency
import hawk.analysis.restlib.contracts.MoneyValue
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class HomeScreenState(
    val lastUpdatedAt: Instant = Clock.System.now(),
    val sum: BigDecimal = BigDecimal.ZERO,
    val profit: BigDecimal = BigDecimal.ZERO,
    val profitRelative: BigDecimal = BigDecimal.ZERO,
    val money: List<MoneyState> = emptyList<MoneyState>(),
    val shares: List<ShareState> = emptyList<ShareState>(),
)

data class SettingsScreenState(
    val profile: UserInfo = UserInfo.default(),
    val accounts: List<AccountInfo> = emptyList(),
    val tokens: List<TokenInfo> = emptyList(),
    val errorUpdateToken: String = "",
    val errorUpdateUser: String = "",
    val errorUpdateAccount: String = "",
)

data class MoneyState(
    val general: Currency,
    val value: MoneyValue,
)

data class ShareState(
    val name: String,
    val sum: BigDecimal,
    val profit: BigDecimal,
    val profitPercent: BigDecimal,
    val count: Int,
    val countOfLots: Int,
    val currencyCode: String,
    val navToAnalyse: () -> Unit = {}
)
