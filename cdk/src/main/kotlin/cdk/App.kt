package cdk

import software.amazon.awscdk.App
import cdk.stacks.DynamoDBStack
import cdk.stacks.WelcomeLambdaStack


fun main() {
    val app = App()
    val dynamoDBStack = DynamoDBStack(app, "DynamoDBStack")
    WelcomeLambdaStack(app, "WelcomeLambdaStack", dynamoDBStack.table)
    app.synth()
}