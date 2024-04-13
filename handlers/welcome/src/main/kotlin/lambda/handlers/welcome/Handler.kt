package lambda.handlers.welcome

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.SQSEvent
import dev.kevinsalazar.exchange.lambda.core.di.coreModule
import dev.kevinsalazar.exchange.lambda.core.domain.entities.User
import dev.kevinsalazar.exchange.lambda.core.domain.utils.json
import kotlinx.coroutines.runBlocking

class Handler : RequestHandler<SQSEvent, Unit> {

    private val emailManager by lazy { coreModule.emailManager }
    private val userRepository by lazy { coreModule.userRepository }

    override fun handleRequest(input: SQSEvent, context: Context) = runBlocking {
        input.records.forEach {
            processPayload(json.decodeFromString(it.body))
        }
    }

    private suspend fun processPayload(payload: Payload) {
        val template = "Welcome"
        val data = hashMapOf(
            "name" to payload.userName
        )
        emailManager.sendTemplatedEmail(template, payload.userEmail, data)
        val user = User(
            id = payload.userId,
            name = payload.userName,
            email = payload.userEmail
        )
        userRepository.save(user)

    }

}
