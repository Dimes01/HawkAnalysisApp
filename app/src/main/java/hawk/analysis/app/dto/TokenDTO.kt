package hawk.analysis.app.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class TokenInfo(
    val id: Int,
    val userId: Int,
    val authToken: String,
    val name: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)

@Serializable
data class CreateTokenRequest(
    val password: String,
    val name: String,
    val authToken: String,
)

@Serializable
data class UpdateTokenRequest(
    val id: Int,
    val name: String,
    val password: String,
    val lastUpdatedAt: Instant
)

@Serializable
data class RemoveTokenRequest(
    val id: Int,
    val password: String
)