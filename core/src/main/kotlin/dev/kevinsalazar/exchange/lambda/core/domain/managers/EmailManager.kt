package dev.kevinsalazar.exchange.lambda.core.domain.managers

interface EmailManager {
    suspend fun sendTemplatedEmail(
        name: String,
        address: String,
        data: HashMap<String, String>
    )
}