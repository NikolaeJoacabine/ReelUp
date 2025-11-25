plugins {
    alias(libs.plugins.reelup.android.library)
    alias(libs.plugins.reelup.koin.android)
}

android {
    namespace = "com.nikol.security"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}