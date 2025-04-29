package hawk.analysis.app.dto

import android.icu.math.BigDecimal
import hawk.analysis.app.utilities.BigDecimalSerializer
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val age: Int,
)

@Serializable
data class Account(
    val user: User,
    @Serializable(with = BigDecimalSerializer::class)
    val sum: BigDecimal
)