package plugins

import com.android.build.api.dsl.ApplicationExtension
import extensions.debugImplementation
import extensions.implementation
import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target){
            with(pluginManager) {
                apply(libs.plugins.kotlin.compose.get().pluginId)
            }
            extensions.configure<ApplicationExtension> {
                buildFeatures {
                    compose = true
                }
            }
            dependencies {
                implementation(platform(libs.androidx.compose.bom))
                implementation(libs.androidx.compose.ui)
                implementation(libs.androidx.compose.ui.graphics)
                implementation(libs.androidx.compose.ui.tooling.preview)
                implementation(libs.androidx.compose.material3)
                implementation(libs.androidx.activity.compose)
                debugImplementation(libs.androidx.compose.ui.tooling)
                debugImplementation(libs.androidx.compose.ui.test.manifest)
            }
        }
    }
}