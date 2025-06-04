package hawk.analysis.app.utilities

import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable

/**
 * Рекомендуется использовать, если не удалось отправить или составить запрос к серверу
 */
class NotSuccessfulRequestException(message: String) : Exception(message)

/**
 * Рекомендуется использовать, когда приходит ответ с кодом 4хх или 5хх
 */
class NotSuccessfulResponseException(val response: HttpResponse, val error: ErrorResponse) : Exception(error.details)

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