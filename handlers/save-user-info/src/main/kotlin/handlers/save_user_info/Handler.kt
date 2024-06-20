package handlers.save_user_info

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.SQSEvent
import dev.kevinsalazar.exchange.lambda.core.di.coreModule
import dev.kevinsalazar.exchange.lambda.core.domain.entities.User
import dev.kevinsalazar.exchange.lambda.core.domain.utils.json
import dev.kevinsalazar.exchange.lambda.core.domain.values.EventBridgeEvent

class Handler : RequestHandler<SQSEvent, Unit> {

    private val userRepository by lazy { coreModule.userRepository }

    override fun handleRequest(input: SQSEvent, context: Context) {
        input.records.forEach {
            println("handlers.save_user_info: ${it.body}")
            processPayload(json.decodeFromString(it.body))
        }
    }

    private fun processPayload(event: EventBridgeEvent<Payload>) {
        val user = User(
            id = event.detail.userId,
            name = event.detail.name,
            email = event.detail.email
        )
        userRepository.save(user)
    }

}
