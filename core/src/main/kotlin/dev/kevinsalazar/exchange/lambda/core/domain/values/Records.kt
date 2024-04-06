package dev.kevinsalazar.exchange.lambda.core.domain.values

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Records(
    @SerialName("Records")
    val records: List<Record>
) {
    @Serializable
    data class Record(
        @SerialName("body")
        val body: String
    )
}