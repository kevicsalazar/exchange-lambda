plugins {
    alias(libs.plugins.kotlinJvm)
    application
}

group = "org.example"
version = "unspecified"

application {
    mainClass.set("cdk.AppKt")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.135.0")
}
