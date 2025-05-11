package hawk.analysis.restlib.utilities

import android.icu.math.BigDecimal
import hawk.analysis.restlib.contracts.AccessLevel
import hawk.analysis.restlib.contracts.AccountStatus
import hawk.analysis.restlib.contracts.AccountType
import hawk.analysis.restlib.contracts.MoneyValue
import hawk.analysis.restlib.contracts.Quotation

fun MoneyValue.toBigDecimal(): BigDecimal {
    val unitsPart = BigDecimal.valueOf(units)
    val nanoPart = BigDecimal.valueOf(nano.toLong(), 9)
    return unitsPart.add(nanoPart)
}

fun Quotation.toBigDecimal(): BigDecimal {
    val unitsPart = BigDecimal.valueOf(units)
    val nanoPart = BigDecimal.valueOf(nano.toLong(), 9)
    return unitsPart.add(nanoPart)
}

val stringToAccessLevel: Map<String, AccessLevel> = mapOf(
    Pair("ACCOUNT_ACCESS_LEVEL_UNSPECIFIED", AccessLevel.ACCOUNT_ACCESS_LEVEL_UNSPECIFIED),
    Pair("ACCOUNT_ACCESS_LEVEL_FULL_ACCESS", AccessLevel.ACCOUNT_ACCESS_LEVEL_FULL_ACCESS),
    Pair("ACCOUNT_ACCESS_LEVEL_READ_ONLY", AccessLevel.ACCOUNT_ACCESS_LEVEL_READ_ONLY),
    Pair("ACCOUNT_ACCESS_LEVEL_NO_ACCESS", AccessLevel.ACCOUNT_ACCESS_LEVEL_NO_ACCESS),
)

val stringToAccountType: Map<String, AccountType> = mapOf(
    Pair("ACCOUNT_TYPE_UNSPECIFIED", AccountType.ACCOUNT_TYPE_UNSPECIFIED),
    Pair("ACCOUNT_TYPE_TINKOFF", AccountType.ACCOUNT_TYPE_TINKOFF),
    Pair("ACCOUNT_TYPE_TINKOFF_IIS", AccountType.ACCOUNT_TYPE_TINKOFF_IIS),
    Pair("ACCOUNT_TYPE_INVEST_BOX", AccountType.ACCOUNT_TYPE_INVEST_BOX),
    Pair("ACCOUNT_TYPE_INVEST_FUND", AccountType.ACCOUNT_TYPE_INVEST_FUND),
)

val stringToAccountStatus: Map<String, AccountStatus> = mapOf(
    Pair("ACCOUNT_STATUS_UNSPECIFIED", AccountStatus.ACCOUNT_STATUS_UNSPECIFIED),
    Pair("ACCOUNT_STATUS_NEW", AccountStatus.ACCOUNT_STATUS_NEW),
    Pair("ACCOUNT_STATUS_OPEN", AccountStatus.ACCOUNT_STATUS_OPEN),
    Pair("ACCOUNT_STATUS_CLOSED", AccountStatus.ACCOUNT_STATUS_CLOSED),
    Pair("ACCOUNT_STATUS_ALL", AccountStatus.ACCOUNT_STATUS_ALL),
)