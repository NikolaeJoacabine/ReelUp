plugins {
    alias(libs.plugins.reelup.android.application)
    alias(libs.plugins.reelup.android.compose.application)
    alias(libs.plugins.reelup.koin.compose)
    alias(libs.plugins.kotlin.serialization)
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
    implementation(projects.core.ui)
    implementation(projects.core.security)

    implementation(projects.feature.auth.authImpl)
    implementation(projects.feature.auth.authApi)
    implementation(projects.feature.home.homeApi)
    implementation(projects.feature.home.homeImpl)
    implementation(projects.feature.detail.detailApi)
    implementation(projects.feature.detail.detailImpl)
    implementation(projects.feature.search.searchApi)
    implementation(projects.feature.search.searchImpl)

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.kotlinx.serialization.json)
}