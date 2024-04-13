package dev.kevinsalazar.exchange.lambda.core.domain.props

interface Properties {
    val region: String
    val sourceEmail: String
    val usersTableName: String
}