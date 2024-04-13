package cdk

import software.amazon.awscdk.App
import cdk.stacks.DynamoDBStack
import cdk.stacks.SaveUserInfoLambdaStack
import cdk.stacks.SendWelcomeEmailLambdaStack


fun main() {
    val app = App()
    val dynamoDBStack = DynamoDBStack(app, "DynamoDBStack")
    SaveUserInfoLambdaStack(app, "SaveUserInfoLambdaStack", dynamoDBStack.table)
    SendWelcomeEmailLambdaStack(app, "SendWelcomeEmailLambdaStack")
    app.synth()
}