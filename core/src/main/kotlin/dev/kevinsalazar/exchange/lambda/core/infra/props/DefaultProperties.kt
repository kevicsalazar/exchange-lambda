package dev.kevinsalazar.exchange.lambda.core.infra.props

import dev.kevinsalazar.exchange.lambda.core.domain.props.Properties

class DefaultProperties : Properties {
    override val region: String
        get() = "us-east-1"
    override val usersTableName: String
        get() = System.getenv("TABLE_NAME")
    override val sourceEmail: String
        get() = System.getenv("SOURCE")
}
