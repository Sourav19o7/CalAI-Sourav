plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply<MainGradlePlugin>()
android {
    namespace = "second.brain.feature_onboarding"


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    testImplementation(libs.junit.v412)

    addFirebaseDependencies()
    addGoogleAuthDependencies()
    addHiltDependencies()
    addComposeDependencies()
    addResourceDependencies()
    addCoreDependencies()
    addNetworkingDependencies()
    addTestDependencies()
    addAndroidTestDependencies()

}