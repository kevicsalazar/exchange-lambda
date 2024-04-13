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

class WelcomeLambdaStack(scope: Construct, id: String, dynamoDBTable: Table) : Stack(scope, id) {

    init {
        val env = mapOf(
            "TABLE_NAME" to dynamoDBTable.tableName,
            "SOURCE" to "kevicsalazar1994@gmail.com"
        )
        val lambdaFunction = Function.Builder.create(this, "WelcomeLambdaFunction")
            .description("Function to send Welcome email")
            .handler("lambda.handlers.welcome.Handler::handleRequest")
            .runtime(Runtime.JAVA_21)
            .code(Code.fromAsset("../handlers/welcome/build/libs/welcome-1.0-SNAPSHOT-all.jar"))
            .architecture(Architecture.ARM_64)
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

        dynamoDBTable.grantReadWriteData(lambdaFunction)
        lambdaFunction.addEventSource(getEventSource())
    }

    private fun getEventSource(): IEventSource {
        val id = "WelcomeSqsEventSource"
        val arn = "arn:aws:sqs:us-east-1:891377324743:retention_send_welcome_email_on_user_registered"
        return SqsEventSource.Builder.create(Queue.fromQueueArn(this, id, arn))
            .batchSize(1)
            .build()
    }
}
