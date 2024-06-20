package handlers.save_user_info

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val userId: String,
    val name: String,
    val email: String
)

@Serializable
data class EventBridgeEvent(
    val detail: Payload,
    val source: String,
    val time: String,
    val id: String
)