plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.shadow)
    application
}

application {
    mainClass.set("lambda.handlers.welcome.Handler")
}

group = "lambda.handlers.welcome"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(projects.core)
    implementation(libs.aws.lambda.core)
    implementation(libs.aws.lambda.events)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.kotlin.test.junit)
}
