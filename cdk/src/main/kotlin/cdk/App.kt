package cdk

import cdk.stacks.DynamoDBStack
import cdk.stacks.SaveUserInfoLambdaStack
import cdk.stacks.SendSwapEmailLambdaStack
import cdk.stacks.SendWelcomeEmailLambdaStack
import software.amazon.awscdk.App


fun main() {
    val app = App()
    val dynamoDBStack = DynamoDBStack(app, "DynamoDBStack")
    SaveUserInfoLambdaStack(app, "SaveUserInfoLambdaStack", dynamoDBStack.table)
    SendWelcomeEmailLambdaStack(app, "SendWelcomeEmailLambdaStack")
    SendSwapEmailLambdaStack(app, "SendSwapEmailLambdaStack", dynamoDBStack.table)
    app.synth()
}