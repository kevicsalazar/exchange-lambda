plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.shadow)
    application
}

application {
    mainClass.set("handlers.save_user_info.Handler")
}

group = "handlers.save_user_info"
version = "1.1"

dependencies {
    implementation(projects.core)
    implementation(libs.aws.lambda.core)
    implementation(libs.aws.lambda.events)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.logback)
    testImplementation(libs.kotlin.test.junit)
}
