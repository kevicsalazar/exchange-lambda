package dev.kevinsalazar.exchange.lambda.core.infra.ddb

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.GetItemRequest
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import dev.kevinsalazar.exchange.lambda.core.domain.entities.User
import dev.kevinsalazar.exchange.lambda.core.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking

class DefaultUserRepository : UserRepository {

    override fun save(user: User) {

        val itemValues = mutableMapOf<String, AttributeValue>()
        itemValues["id"] = AttributeValue.S(user.id)
        itemValues["name"] = AttributeValue.S(user.name)
        itemValues["email"] = AttributeValue.S(user.email)

        val request = PutItemRequest {
            tableName = "users"
            item = itemValues
        }

        ddbClient { ddb ->
            ddb.putItem(request)
        }
    }

    override fun find(id: String): User? {

        val keyToGet = mutableMapOf<String, AttributeValue>()
        keyToGet["id"] = AttributeValue.S(id)

        val request = GetItemRequest {
            key = keyToGet
            tableName = "users"
        }

        val response = ddbClient { ddb ->
            ddb.getItem(request).item
        }

        if (response == null) return null

        val user = User(
            id = response["id"]?.asS().orEmpty(),
            name = response["name"]?.asS().orEmpty(),
            email = response["email"]?.asS().orEmpty(),
        )

        return user
    }

    private fun <T> ddbClient(block: suspend (DynamoDbClient) -> T): T {
        return DynamoDbClient { region = "us-east-1" }
            .use { ddb -> runBlocking { block.invoke(ddb) } }
    }

}