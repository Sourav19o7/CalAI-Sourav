import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    `kotlin-dsl`
}


allprojects{
    repositories {
        google()
        mavenCentral()

        maven {
            url = uri("https://jitpack.io")

        }

        maven {
            url = uri("https://developer.huawei.com/repo/")
}
    }
}


dependencies {
    // do not update!!!!!
    implementation("com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:1.9.24-1.0.20")
    implementation("com.squareup:javapoet:1.13.0")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.24")
    implementation("com.android.tools.build:gradle:8.5.2")
    implementation("io.gitlab.arturbosch.detekt:detekt-gradle-plugin:1.23.1")

    // do not update
    // serializable plugin is not compatible with 1.9.20!
    implementation("org.jetbrains.kotlin:kotlin-serialization:1.9.10")
//    implementation("com.google.gms:google-services:4.4.2")
    implementation("org.jetbrains.dokka:dokka-gradle-plugin:1.9.10")
//    implementation("com.google.firebase:firebase-crashlytics:18.6.2")


}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "21"
}
