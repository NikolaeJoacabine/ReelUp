plugins {
    alias(libs.plugins.reelup.android.library)
    alias(libs.plugins.reelup.android.compose.library)
    `maven-publish`
}

android {
    namespace = "com.nikol.viewmodel"
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(projects.core.navigation.navApi)
}

