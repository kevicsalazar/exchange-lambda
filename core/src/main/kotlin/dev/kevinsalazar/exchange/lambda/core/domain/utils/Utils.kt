package dev.kevinsalazar.exchange.lambda.core.domain.utils

import kotlinx.serialization.json.Json
import java.text.NumberFormat
import java.util.*


val json = Json {
    ignoreUnknownKeys = true
}

fun formatAmount(code: String, amount: Double): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    val formattedAmount = numberFormat.format(amount)
    return "$formattedAmount $code"
}