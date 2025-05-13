package hawk.analysis.restlib.contracts

import kotlinx.serialization.Serializable

@Serializable
data class MoneyValue(
    val currency: String,
    val units: Long,
    val nano: Int,
)

@Serializable
data class Quotation(
    val units: Long,
    val nano: Int,
)

/**
 * @param logoName путь к лого
 * @param logoBaseColor цвет чего-то...
 * @param textColor цвет текста
 */
@Serializable
data class BrandData(
    val logoName: String,
    val logoBaseColor: String,
    val textColor: String
)

@Serializable
data class ErrorDetail(
    val code: String,
    val message: String,
)