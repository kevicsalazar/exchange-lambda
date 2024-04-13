package handlers.send_swap_email

import kotlinx.serialization.Serializable

@Serializable
data class Payload(
    val transactionId: String,
    val userId: String,
    val sentCurrencyCode: String? = null,
    val sentAmount: Double? = null,
    val receivedCurrencyCode: String? = null,
    val receivedAmount: Double? = null,
    val created: String
)