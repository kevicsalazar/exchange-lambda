plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.shadow)
    application
}

application {
    mainClass.set("handlers.send_swap_email.Handler")
}

group = "handlers.send_swap_email"

dependencies {
    implementation(projects.core)
    implementation(libs.aws.lambda.core)
    implementation(libs.aws.lambda.events)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.logback)
    testImplementation(libs.kotlin.test.junit)
}
