plugins {
    alias(libs.plugins.reelup.android.application)
    alias(libs.plugins.reelup.android.compose.application)
    alias(libs.plugins.reelup.koin.compose)
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.ktor)
    implementation(libs.navigation.compose)

    implementation(projects.core.navigation.navApi)
    implementation(projects.core.navigation.navImpl)
    implementation(projects.core.network)
    implementation(projects.core.viewModel)
    implementation(projects.core.di)
    implementation(projects.core.security)

    implementation(projects.feature.auth.authImpl)
    implementation(projects.feature.auth.authApi)
    implementation(projects.feature.home.homeApi)
    implementation(projects.feature.home.homeImpl)
}