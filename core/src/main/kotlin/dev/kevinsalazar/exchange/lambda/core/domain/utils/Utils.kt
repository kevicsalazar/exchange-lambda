package dev.kevinsalazar.exchange.lambda.core.domain.utils

import kotlinx.serialization.json.Json


val json = Json {
    ignoreUnknownKeys = true
}
