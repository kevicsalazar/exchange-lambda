package dev.kevinsalazar.exchange.lambda.core.domain.managers

interface EmailManager {
    fun sendTemplatedEmail(
        name: String,
        address: String,
        data: HashMap<String, String>
    )
}