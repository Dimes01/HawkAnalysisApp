package hawk.analysis.app.utilities

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val details: String,
    val entity: String? = null,
    val message: String? = null,
    val parameter: String? = null,
)

data class HawkResponse<T>(
    val response: T?,
    val error: ErrorResponse?
)