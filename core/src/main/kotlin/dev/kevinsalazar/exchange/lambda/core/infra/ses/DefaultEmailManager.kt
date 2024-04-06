package dev.kevinsalazar.exchange.lambda.core.infra.ses

import aws.sdk.kotlin.services.ses.SesClient
import aws.sdk.kotlin.services.ses.model.Destination
import aws.sdk.kotlin.services.ses.sendTemplatedEmail
import dev.kevinsalazar.exchange.lambda.core.domain.managers.EmailManager
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DefaultEmailManager : EmailManager {
    override fun sendTemplatedEmail(
        name: String,
        address: String,
        data: HashMap<String, String>
    ) {
        SesClient { region = "us-east-1" }.use { ses ->
            runBlocking {
                ses.sendTemplatedEmail {
                    source = System.getenv("source")
                    template = name
                    destination = Destination {
                        toAddresses = listOf(address)
                    }
                    templateData = Json.encodeToString(data)
                }
            }
        }
    }
}