package dev.kevinsalazar.exchange.lambda.core.di

import dev.kevinsalazar.exchange.lambda.core.domain.managers.EmailManager
import dev.kevinsalazar.exchange.lambda.core.domain.repository.UserRepository
import dev.kevinsalazar.exchange.lambda.core.infra.ddb.DefaultUserRepository
import dev.kevinsalazar.exchange.lambda.core.infra.ses.DefaultEmailManager

interface CoreModule {
    val userRepository: UserRepository
    val emailManager: EmailManager
}

class DefaultCoreModule : CoreModule {

    override val userRepository by lazy {
        DefaultUserRepository()
    }

    override val emailManager by lazy {
        DefaultEmailManager()
    }
}

val coreModule: CoreModule by lazy {
    DefaultCoreModule()
}