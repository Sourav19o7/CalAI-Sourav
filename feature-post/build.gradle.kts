plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
}

apply<MainGradlePlugin>()
android {
    namespace = "second.brain.feature_post"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)

    addFirebaseDependencies()
    addGoogleAuthDependencies()
    addHiltDependencies()
    addComposeDependencies()
    addResourceDependencies()
    addCoreDependencies()
    addSerializationDependencies()
    addNetworkingDependencies()

}