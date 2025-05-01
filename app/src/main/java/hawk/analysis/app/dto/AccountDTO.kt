package hawk.analysis.app.dto

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