import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class MainGradlePlugin : Plugin<Project>{

    override fun apply(target: Project) {
        applyPlugins(target)
        setProjectConfig(target)
    }

    private fun applyPlugins(project: Project){

        project.apply{
            plugin("android-library")
            plugin("kotlin-android")
            plugin("com.google.devtools.ksp")
            plugin("dagger.hilt.android.plugin")
            plugin("io.gitlab.arturbosch.detekt")
            plugin("org.jetbrains.dokka")

        }
    }
    private fun setProjectConfig(project: Project){




        project.android().apply {


            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    merges += "/META-INF/LICENSE.md"
                    merges += "/META-INF/LICENSE-notice.md"
                }
            }

            compileSdk = ProjectConfig.COMPILE_SDK



            defaultConfig {
                minSdk = ProjectConfig.MIN_SDK
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }


            defaultConfig{
                multiDexEnabled = true
                testInstrumentationRunner = "second.brain.AndroidHiltTestRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
            }


            composeOptions {
                kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
            }


            buildFeatures {
                compose = true
            }

        }
    }

    private fun Project.android() : LibraryExtension{
        return extensions.getByType(LibraryExtension::class.java)
    }
}