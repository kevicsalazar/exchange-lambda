package handlers.send_welcome_email

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.SQSEvent
import dev.kevinsalazar.exchange.lambda.core.di.coreModule
import dev.kevinsalazar.exchange.lambda.core.domain.utils.json
import dev.kevinsalazar.exchange.lambda.core.domain.values.EventBridgeEvent

class Handler : RequestHandler<SQSEvent, Unit> {

    private val emailManager by lazy { coreModule.emailManager }

    override fun handleRequest(input: SQSEvent, context: Context) {
        input.records.forEach {
            println("handlers.send_welcome_email: ${it.body}")
            processPayload(json.decodeFromString(it.body))
        }
    }

    private fun processPayload(event: EventBridgeEvent<Payload>) {
        val template = "Welcome"
        val data = hashMapOf(
            "name" to event.detail.userName
        )
        emailManager.sendTemplatedEmail(template, event.detail.userEmail, data)
    }
}
