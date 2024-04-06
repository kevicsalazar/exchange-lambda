package dev.kevinsalazar.exchange.lambda.core.domain.repository

import dev.kevinsalazar.exchange.lambda.core.domain.entities.User

interface UserRepository {
    fun save(user: User)
    fun find(id: String): User?
}