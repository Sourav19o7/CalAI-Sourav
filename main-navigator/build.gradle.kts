plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

apply<MainGradlePlugin>()
android {
    namespace = "second.brain.main_navigator"


}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)

    addHiltDependencies()

    addFeatureDependencies()
    addCoreDependencies()
    addComposeDependencies()
    addResourceDependencies()

}