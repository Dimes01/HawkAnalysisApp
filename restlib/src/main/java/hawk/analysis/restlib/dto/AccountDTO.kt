package hawk.analysis.restlib.dto

import hawk.analysis.restlib.enums.AccountStatus
import hawk.analysis.restlib.models.Account
import hawk.analysis.restlib.serializers.AccountStatusSerializer
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