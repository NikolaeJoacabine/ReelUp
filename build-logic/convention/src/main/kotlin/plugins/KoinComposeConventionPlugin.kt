package plugins

import extensions.implementation
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.koin.androidx.compose)
            }
        }
    }
}