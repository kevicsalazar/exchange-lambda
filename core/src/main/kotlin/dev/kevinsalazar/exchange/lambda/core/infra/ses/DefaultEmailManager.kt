package dev.kevinsalazar.exchange.lambda.core.infra.ses

import aws.sdk.kotlin.services.ses.SesClient
import aws.sdk.kotlin.services.ses.model.Destination
import aws.sdk.kotlin.services.ses.sendTemplatedEmail
import dev.kevinsalazar.exchange.lambda.core.domain.managers.EmailManager
import dev.kevinsalazar.exchange.lambda.core.domain.props.Properties
import dev.kevinsalazar.exchange.lambda.core.domain.utils.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.encodeToString

class DefaultEmailManager(
    private val props: Properties
) : EmailManager {
    override fun sendTemplatedEmail(
        name: String,
        address: String,
        data: HashMap<String, String>
    ) {
        SesClient { region = props.region }.use { ses ->
            runBlocking {
                ses.sendTemplatedEmail {
                    source = props.sourceEmail
                    template = name
                    destination = Destination {
                        toAddresses = listOf(address)
                    }
                    templateData = json.encodeToString(data)
                }
            }
        }
    }
}