package hawk.analysis.app.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class AnalyseRequest(val accountId: String)

@Serializable
data class AssetAnalyse(
    val id: Int,
    val accountId: String,
    val securitiesId: String,
    val dateFrom: Instant,
    val dateTo: Instant,
    override val mean: Double,
    override val stdDev: Double,
    override val variation: Double?,
    override val sharp: Double?,
    override val information: Double?,
    override val sortino: Double?
) : AnalyseInfo

@Serializable
data class AccountAnalyse(
    val id: Int,
    val accountId: String,
    val dateFrom: Instant,
    val dateTo: Instant,
    override val mean: Double,
    override val stdDev: Double,
    override val variation: Double?,
    override val sharp: Double?,
    override val information: Double?,
    override val sortino: Double?
) : AnalyseInfo

interface AnalyseInfo {
    val mean: Double
    val stdDev: Double
    val variation: Double?
    val sharp: Double?
    val information: Double?
    val sortino: Double?
}