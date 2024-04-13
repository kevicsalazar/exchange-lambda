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
            processPayload(json.decodeFromString(it.body))
        }
    }

    private fun processPayload(payload: Payload) {
        val user = User(
            id = payload.userId,
            name = payload.userName,
            email = payload.userEmail
        )
        userRepository.save(user)
    }

}
