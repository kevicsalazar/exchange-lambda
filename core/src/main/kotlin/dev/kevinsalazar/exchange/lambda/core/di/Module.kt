package dev.kevinsalazar.exchange.lambda.core.di

import dev.kevinsalazar.exchange.lambda.core.domain.managers.EmailManager
import dev.kevinsalazar.exchange.lambda.core.domain.props.Properties
import dev.kevinsalazar.exchange.lambda.core.domain.repository.UserRepository
import dev.kevinsalazar.exchange.lambda.core.infra.ddb.DefaultUserRepository
import dev.kevinsalazar.exchange.lambda.core.infra.props.DefaultProperties
import dev.kevinsalazar.exchange.lambda.core.infra.ses.DefaultEmailManager

interface CoreModule {
    val userRepository: UserRepository
    val emailManager: EmailManager
    val properties: Properties
}

class DefaultCoreModule : CoreModule {

    override val userRepository by lazy {
        DefaultUserRepository(properties)
    }

    override val emailManager by lazy {
        DefaultEmailManager(properties)
    }

    override val properties by lazy {
        DefaultProperties()
    }
}

val coreModule: CoreModule by lazy {
    DefaultCoreModule()
}