package hawk.analysis.app.utilities

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val details: String,
    val entity: String? = null,
    val message: String? = null,
    val parameter: String? = null,
)

@Serializable
data class ErrorResponseTI(
    val code: Int,
    val message: String,
    val description: Int,
)

data class HawkResponse<T, D>(
    val response: T?,
    val error: D?
)