plugins {
    alias(libs.plugins.reelup.android.feature)
}

android {
    namespace = "com.nikol.detail_impl"
}
dependencies {
    implementation(projects.core.domainUtil)
    implementation(projects.core.security)
    implementation(projects.core.network)
    implementation(projects.core.di)
    implementation(projects.core.navigation.navApi)
    implementation(projects.core.navigation.navImpl)
    implementation(projects.core.ui)
    implementation(projects.core.viewModel)
    implementation(projects.feature.detail.detailApi)
    implementation(libs.coil.network.ktor)
    implementation(libs.coil.compose)
}