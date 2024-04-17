rootProject.name = "exchange-lambda"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include("core")
include("handlers:send-welcome-email")
include("handlers:send-swap-email")
include("handlers:save-user-info")
