package hawk.analysis.restlib.contracts

import hawk.analysis.restlib.utilities.AccessLevelSerializer
import hawk.analysis.restlib.utilities.AccountStatusSerializer
import hawk.analysis.restlib.utilities.AccountTypeSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class GetAccountsRequest(
    @Serializable(with = AccountStatusSerializer::class)
    val status: AccountStatus
)

@Serializable
data class GetAccountsResponse(
    val accounts: List<Account>
)

@Serializable
data class Account(
    val id: String,
    @Serializable(with = AccountTypeSerializer::class)
    val type: AccountType,
    val name: String,
    @Serializable(with = AccountStatusSerializer::class)
    val status: AccountStatus,
    val openedDate: Instant,
    val closedDate: Instant,
    @Serializable(with = AccessLevelSerializer::class)
    val accessLevel: AccessLevel
)

enum class AccountType(val value: Int) {
    ACCOUNT_TYPE_UNSPECIFIED(0),
    ACCOUNT_TYPE_TINKOFF(1),
    ACCOUNT_TYPE_TINKOFF_IIS(2),
    ACCOUNT_TYPE_INVEST_BOX(3),
    ACCOUNT_TYPE_INVEST_FUND(4),
}

enum class AccountStatus(val value: Int) {
    ACCOUNT_STATUS_UNSPECIFIED(0),
    ACCOUNT_STATUS_NEW(1),
    ACCOUNT_STATUS_OPEN(2),
    ACCOUNT_STATUS_CLOSED(3),
    ACCOUNT_STATUS_ALL(4)
}

enum class AccessLevel(val value: Int) {
    ACCOUNT_ACCESS_LEVEL_UNSPECIFIED(0),
    ACCOUNT_ACCESS_LEVEL_FULL_ACCESS(1),
    ACCOUNT_ACCESS_LEVEL_READ_ONLY(2),
    ACCOUNT_ACCESS_LEVEL_NO_ACCESS(3)
}