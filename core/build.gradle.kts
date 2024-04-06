plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.logback)
    implementation("aws.sdk.kotlin:ses:1.0.30")
    implementation("aws.sdk.kotlin:secretsmanager:1.0.30")
    implementation(libs.aws.dynamodb)
    testImplementation(kotlin("test"))
}
