plugins {
    alias(libs.plugins.reelup.android.library)
    alias(libs.plugins.reelup.android.compose.library)
    alias(libs.plugins.reelup.koin.compose)
}

android {
    namespace = "com.nikol.di"
}

dependencies {
    implementation(libs.navigation.compose)
    implementation(projects.core.navigation.navImpl)
    implementation(projects.core.viewModel)
}