package dev.kevinsalazar.exchange.lambda.core.domain.values

import kotlinx.serialization.Serializable

@Serializable
data class EventBridgeEvent<T>(
    val detail: T,
    val source: String,
    val time: String,
    val id: String
)
