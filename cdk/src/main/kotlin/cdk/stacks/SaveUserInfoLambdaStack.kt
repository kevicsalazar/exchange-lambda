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

class SaveUserInfoLambdaStack(scope: Construct, id: String, dynamoDBTable: Table) : Stack(scope, id) {

    init {
        val env = mapOf(
            "TABLE_NAME" to dynamoDBTable.tableName
        )
        val lambdaFunction = Function.Builder.create(this, "SaveUserInfo")
            .description("Function to save user info")
            .handler("handlers.save_user_info.Handler::handleRequest")
            .runtime(Runtime.JAVA_21)
            .code(Code.fromAsset("../handlers/save-user-info/build/libs/save-user-info-1.0-all.jar"))
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
        val id = "SaveUserInfoSqsEventSource"
        val arn = "arn:aws:sqs:us-east-1:891377324743:retention_save_user_info_on_user_registered"
        return SqsEventSource.Builder.create(Queue.fromQueueArn(this, id, arn))
            .batchSize(1)
            .build()
    }
}
