package handlers.send_swap_email

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.SQSEvent
import dev.kevinsalazar.exchange.lambda.core.di.coreModule
import dev.kevinsalazar.exchange.lambda.core.domain.entities.User
import dev.kevinsalazar.exchange.lambda.core.domain.utils.formatAmount
import dev.kevinsalazar.exchange.lambda.core.domain.utils.json
import dev.kevinsalazar.exchange.lambda.core.domain.values.EventBridgeEvent

class Handler : RequestHandler<SQSEvent, Unit> {

    private val emailManager by lazy { coreModule.emailManager }
    private val userRepository by lazy { coreModule.userRepository }

    override fun handleRequest(input: SQSEvent, context: Context) {
        input.records.forEach {
            processPayload(json.decodeFromString(it.body))
        }
    }

    private fun processPayload(event: EventBridgeEvent<Payload>) {

        val (sentAmount, receivedAmount) = getAmounts(event.detail)

        val user = getUserInfo(event.detail.userId)

        val template = "Swap"
        val data = hashMapOf(
            "name" to user.name,
            "sentAmount" to sentAmount,
            "receivedAmount" to receivedAmount,
        )
        emailManager.sendTemplatedEmail(template, user.email, data)
    }

    private fun getAmounts(payload: Payload): Pair<String, String> {

        val sentCurrencyCode = requireNotNull(payload.sentCurrencyCode)
        val sentAmount = requireNotNull(payload.sentAmount)
        val receivedCurrencyCode = requireNotNull(payload.receivedCurrencyCode)
        val receivedAmount = requireNotNull(payload.receivedAmount)

        val sentAmountFormatted = formatAmount(sentCurrencyCode, sentAmount)
        val receivedAmountFormatted = formatAmount(receivedCurrencyCode, receivedAmount)

        return sentAmountFormatted to receivedAmountFormatted
    }

    private fun getUserInfo(id: String): User {
        return requireNotNull(userRepository.find(id))
    }

}
