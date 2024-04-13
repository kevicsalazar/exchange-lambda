package cdk.stacks

import software.amazon.awscdk.Duration
import software.amazon.awscdk.Stack
import software.amazon.awscdk.services.iam.PolicyStatement
import software.amazon.awscdk.services.lambda.*
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.eventsources.SqsEventSource
import software.amazon.awscdk.services.sqs.Queue
import software.constructs.Construct

class SendWelcomeEmailLambdaStack(scope: Construct, id: String) : Stack(scope, id) {

    init {
        val env = mapOf(
            "SOURCE" to "kevicsalazar1994@gmail.com"
        )
        val lambdaFunction = Function.Builder.create(this, "SendWelcomeEmail")
            .description("Function to send welcome email")
            .handler("handlers.send_welcome_email.Handler::handleRequest")
            .runtime(Runtime.JAVA_21)
            .code(Code.fromAsset("../handlers/send-welcome-email/build/libs/send-welcome-email-1.0-all.jar"))
            .architecture(Architecture.X86_64)
            .snapStart(SnapStartConf.ON_PUBLISHED_VERSIONS)
            .memorySize(512)
            .timeout(Duration.seconds(15))
            .environment(env)
            .build()

        lambdaFunction.addToRolePolicy(
            PolicyStatement.Builder.create()
                .actions(listOf("ses:*"))
                .resources(listOf("*"))
                .build()
        )
        lambdaFunction.addEventSource(getEventSource())
    }

    private fun getEventSource(): IEventSource {
        val id = "SendWelcomeEmailSqsEventSource"
        val arn = "arn:aws:sqs:us-east-1:891377324743:retention_send_welcome_email_on_user_registered"
        return SqsEventSource.Builder.create(Queue.fromQueueArn(this, id, arn))
            .batchSize(1)
            .build()
    }
}
