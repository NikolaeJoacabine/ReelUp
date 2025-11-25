plugins {
    alias(libs.plugins.reelup.android.library)
    alias(libs.plugins.reelup.android.compose.library)
    alias(libs.plugins.reelup.koin.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.nikol.nav_impl"
}

dependencies {
    api(projects.core.navigation.navApi)
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}