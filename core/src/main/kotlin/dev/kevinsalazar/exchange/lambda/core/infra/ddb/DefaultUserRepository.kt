package dev.kevinsalazar.exchange.lambda.core.infra.ddb

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.GetItemRequest
import aws.sdk.kotlin.services.dynamodb.model.PutItemRequest
import dev.kevinsalazar.exchange.lambda.core.domain.entities.User
import dev.kevinsalazar.exchange.lambda.core.domain.props.Properties
import dev.kevinsalazar.exchange.lambda.core.domain.repository.UserRepository
import kotlinx.coroutines.runBlocking

class DefaultUserRepository(
    private val props: Properties
) : UserRepository {

    override fun save(user: User) {

        val values = mapOf<String, AttributeValue>(
            "id" to AttributeValue.S(user.id),
            "name" to AttributeValue.S(user.name),
            "email" to AttributeValue.S(user.email)
        )

        val request = PutItemRequest {
            tableName = props.usersTableName
            item = values
        }

        ddbClient { ddb ->
            ddb.putItem(request)
        }
    }

    override fun find(id: String): User? {

        val keyToGet = mapOf<String, AttributeValue>(
            "id" to AttributeValue.S(id)
        )

        val request = GetItemRequest {
            key = keyToGet
            tableName = props.usersTableName
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
        return DynamoDbClient { region = props.region }
            .use { ddb -> runBlocking { block.invoke(ddb) } }
    }

}