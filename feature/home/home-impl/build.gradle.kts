plugins {
    alias(libs.plugins.reelup.android.feature)
}

android {
    namespace = "com.nikol.home_impl"
}

dependencies {
    implementation(projects.core.domainUtil)
    implementation(projects.core.security)
    implementation(projects.core.network)
    implementation(projects.core.di)
    implementation(projects.core.navigation.navApi)
    implementation(projects.core.navigation.navImpl)
    implementation(projects.feature.home.homeApi)
    implementation(projects.core.viewModel)
}