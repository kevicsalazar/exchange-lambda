package handlers.send_welcome_email

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val userId: String,
    val name: String,
    val email: String
)