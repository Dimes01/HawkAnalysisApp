package hawk.analysis.restlib.models

import hawk.analysis.restlib.enums.AccessLevel
import hawk.analysis.restlib.enums.AccountStatus
import hawk.analysis.restlib.enums.AccountType
import hawk.analysis.restlib.serializers.AccessLevelSerializer
import hawk.analysis.restlib.serializers.AccountStatusSerializer
import hawk.analysis.restlib.serializers.AccountTypeSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Account(
    val id: String,
    @Serializable(AccountTypeSerializer::class)
    val type: AccountType,
    val name: String,
    @Serializable(with = AccountStatusSerializer::class)
    val status: AccountStatus,
    val openedDate: Instant,
    val closedDate: Instant,
    @Serializable(with = AccessLevelSerializer::class)
    val accessLevel: AccessLevel
)