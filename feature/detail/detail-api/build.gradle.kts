plugins {
    alias(libs.plugins.reelup.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.nikol.detail_api"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}