package handlers.save_user_info

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.SQSEvent
import dev.kevinsalazar.exchange.lambda.core.di.coreModule
import dev.kevinsalazar.exchange.lambda.core.domain.entities.User
import dev.kevinsalazar.exchange.lambda.core.domain.utils.json

class Handler : RequestHandler<SQSEvent, Unit> {

    private val userRepository by lazy { coreModule.userRepository }

    override fun handleRequest(input: SQSEvent, context: Context) {
        input.records.forEach {
            val eventBridgeEvent = json.decodeFromString<EventBridgeEvent>(it.body)
            println("SAVE USER HANDLER 2: ${it.body}")
            processPayload(eventBridgeEvent.detail)
        }
    }

    private fun processPayload(payload: Payload) {
        val user = User(
            id = payload.userId,
            name = payload.name,
            email = payload.email
        )
        userRepository.save(user)
    }

}
