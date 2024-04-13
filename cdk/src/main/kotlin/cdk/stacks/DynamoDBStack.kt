package cdk.stacks

import software.amazon.awscdk.Stack
import software.amazon.awscdk.services.dynamodb.Attribute
import software.amazon.awscdk.services.dynamodb.AttributeType
import software.amazon.awscdk.services.dynamodb.Table
import software.constructs.Construct

class DynamoDBStack(scope: Construct, id: String) : Stack(scope, id) {

    val table: Table = Table.Builder.create(this, "DynamoDBTable")
        .tableName("users")
        .partitionKey(Attribute.builder().name("id").type(AttributeType.STRING).build())
        .build()
}