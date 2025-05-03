package hawk.analysis.app.dto

import android.icu.math.BigDecimal
import hawk.analysis.app.utilities.BigDecimalSerializer
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class AccountInfo(
    val id: String,
    val userId: Int,
    val openedDate: Instant,
    val closedDate: Instant,
    val type: Int,
    val name: String,
    val status: Int,
    val accessLevel: Int,

    @Serializable(with = BigDecimalSerializer::class)
    val riskFree: BigDecimal?,

    val figiBenchmark: String?,
    val updatedAt: Instant,
)

@Serializable
data class UpdateRiskFreeRequest(
    val accountId: String,
    @Serializable(with = BigDecimalSerializer::class)
    val riskFree: BigDecimal?,
    val lastUpdatedAt: Instant
)

@Serializable
data class UpdateBenchmarkRequest(
    val accountId: String,
    val figiBenchmark: String?,
    val lastUpdatedAt: Instant
)