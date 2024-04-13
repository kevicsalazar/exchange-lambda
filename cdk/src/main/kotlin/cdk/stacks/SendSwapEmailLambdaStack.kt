package cdk.stacks

import software.amazon.awscdk.Duration
import software.amazon.awscdk.Stack
import software.amazon.awscdk.services.dynamodb.Table
import software.amazon.awscdk.services.iam.PolicyStatement
import software.amazon.awscdk.services.lambda.*
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.lambda.eventsources.SqsEventSource
import software.amazon.awscdk.services.sqs.Queue
import software.constructs.Construct

class SendSwapEmailLambdaStack(scope: Construct, id: String, dynamoDBTable: Table) : Stack(scope, id) {

    init {
        val env = mapOf(
            "TABLE_NAME" to dynamoDBTable.tableName,
            "SOURCE" to "kevicsalazar1994@gmail.com"
        )
        val lambdaFunction = Function.Builder.create(this, "SendSwapEmail")
            .description("Function to send swap email")
            .handler("handlers.send_swap_email.Handler::handleRequest")
            .runtime(Runtime.JAVA_21)
            .code(Code.fromAsset("../handlers/send-swap-email/build/libs/send-swap-email-1.0-all.jar"))
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
        dynamoDBTable.grantReadData(lambdaFunction)
        lambdaFunction.addEventSource(getEventSource())
    }

    private fun getEventSource(): IEventSource {
        val id = "SendSwapEmailSqsEventSource"
        val arn = "arn:aws:sqs:us-east-1:891377324743:retention_send_swap_email_on_swap_succeed"
        return SqsEventSource.Builder.create(Queue.fromQueueArn(this, id, arn))
            .batchSize(1)
            .build()
    }
}
