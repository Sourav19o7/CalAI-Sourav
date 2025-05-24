plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization")
    id("kotlin-parcelize")

}

apply<MainGradlePlugin>()

android {
    namespace = "second.brain.core"
}

dependencies {

    implementation(kotlin("reflect"))
    testImplementation(libs.junit)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    testImplementation(libs.junit)

    //json dependencies has to be added here
    //as the buildSrc file does not support api and runtimeOnly dependencies
    api(libs.jjwt.api)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly("io.jsonwebtoken:jjwt-orgjson:0.12.3") {
        exclude(group = "org.json", module =  "json") //provided by Android natively
    }
    implementation(libs.jjwt.api)
    addHiltDependencies()
    addComposeDependencies()
    addResourceDependencies()
    addSerializationDependencies()
    addFirebaseDependencies()
    addNetworkingDependencies()
    addImageDependencies()

}
