package hawk.analysis.app.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Analyse(
    val id: Int,
    val accountId: String,
    val securitiesId: String,
    val dateFrom: Instant,
    val dateTo: Instant,
    val mean: Double,
    val stdDev: Double,
    val variation: Double?,
    val sharp: Double?,
    val information: Double?,
    val sortino: Double?
)