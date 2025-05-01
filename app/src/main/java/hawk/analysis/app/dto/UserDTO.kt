package hawk.analysis.app.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
)

@Serializable
data class RegisterResponse(
    val id: Int,
    val name: String,
    val email: String,
    val jwtToken: String,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class LoginResponse(
    val id: Int,
    val name: String,
    val email: String,
    val jwtToken: String,
)

@Serializable
data class UserInfo(
    val id: Int,
    val name: String,
    val email: String,
    val updatedAt: Instant,
    val createdAt: Instant,
)

@Serializable
data class UpdateEmailRequest(
    val lastUpdatedAt: Instant,
    val password: String,
    val newEmail: String,
)

@Serializable
data class UpdatePasswordRequest(
    val lastUpdatedAt: Instant,
    val oldPassword: String,
    val newPassword: String,
)

@Serializable
data class UpdateTokenRequest(
    val lastUpdatedAt: Instant,
    val password: String,
    val newToken: String,
)

@Serializable
data class RecoverRequest(
    val lastUpdatedAt: Instant,
)

@Serializable
data class RecoverResponse(
    val canRecover: Boolean,
    val wasRecovered: Boolean,
)