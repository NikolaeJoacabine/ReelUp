plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(gradleApi())
    implementation(files((libs).javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.plugin.kotlin.compose)
    implementation(libs.plugin.android.application)
    implementation(libs.plugin.kotlin.android)
    implementation(libs.plugin.android.libraty)
    implementation(libs.plugin.kotlin.serialization)
    implementation(libs.plugin.jetbrains.kotlin.jvm)
}

gradlePlugin {
    plugins {
        register("androidAplicationCompose") {
            id = libs.plugins.reelup.android.application.get().pluginId
            implementationClass = "plugins.AndroidApplicationConventionPlugin"
        }
        register("androidApplication") {
            id = libs.plugins.reelup.android.compose.application.get().pluginId
            implementationClass = "plugins.AndroidApplicationComposeConventionPlugin"
        }
        register("androidFeature") {
            id = libs.plugins.reelup.android.feature.get().pluginId
            implementationClass = "plugins.AndroidFeatureConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.reelup.android.compose.library.get().pluginId
            implementationClass = "plugins.AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.reelup.android.library.get().pluginId
            implementationClass = "plugins.AndroidLibraryConventionPlugin"
        }
        register("koinAndroid") {
            id = libs.plugins.reelup.koin.android.get().pluginId
            implementationClass = "plugins.KoinAndroidConventionPlugin"
        }
        register("koinCompose") {
            id = libs.plugins.reelup.koin.compose.get().pluginId
            implementationClass = "plugins.KoinComposeConventionPlugin"
        }
        register("jwmLibrary") {
            id = libs.plugins.reelup.jwm.library.get().pluginId
            implementationClass = "plugins.JvmLibraryConventionPlugin"
        }
    }
}