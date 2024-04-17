package handlers.save_user_info

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val userId: String,
    val name: String,
    val email: String
)