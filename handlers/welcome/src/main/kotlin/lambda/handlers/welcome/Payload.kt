package lambda.handlers.welcome

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    @SerialName("userId")
    val userId: String,
    @SerialName("userName")
    val userName: String,
    @SerialName("userEmail")
    val userEmail: String
)