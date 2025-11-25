import java.util.Properties

plugins {
    alias(libs.plugins.reelup.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.nikol.network"
    defaultConfig {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        val apiKey = properties.getProperty("TMDB_API_KEY") ?: error("TMDB_API_KEY missing!")

        buildConfigField(
            "String",
            "TMDB_API_KEY",
            "\"$apiKey\""
        )
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.ktor.bom))
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okHttp)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.client.resources)
    implementation(libs.koin.android)
    implementation(libs.coil.network.ktor)

    implementation(platform(libs.arrow.bom))
    implementation(libs.arrow.core)

    implementation(projects.core.security)
}
