plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.shadow)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.aws.ses)
    implementation(libs.aws.secrets)
    implementation(libs.aws.dynamodb)
    implementation(libs.logback)
    testImplementation(kotlin("test"))
}
