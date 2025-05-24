plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    id("com.google.firebase.crashlytics")
    id("com.google.firebase.firebase-perf")
    kotlin("plugin.serialization")
    id("androidx.baselineprofile")
}



android {
    namespace = "second.brain"
    compileSdk = ProjectConfig.COMPILE_SDK

    lint {
        abortOnError = false
    }

    defaultConfig {


        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64") // Add only the architectures we want, excluding risc-v
        }
        multiDexEnabled = true
        applicationId = ProjectConfig.APP_ID
        minSdk = ProjectConfig.MIN_SDK
        targetSdk = ProjectConfig.TARGET_SDK
        versionCode = ProjectConfig.VERSION_CODE
        versionName = ProjectConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {


        debug {
            version = "01.00.01"
               versionNameSuffix = "-debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.process)
    implementation(libs.androidx.activity.compose)
    // added this so that the cast from LocalContext to AppCompatActivity
    // would be successful(just for the image picker)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat.v161)
    implementation(libs.material.v1110)
    implementation(platform(libs.androidx.compose.bom.v20240400))
    implementation(libs.androidx.profileinstaller.v131)
    implementation(libs.installreferrer)
    implementation(libs.androidx.compose.ui.ui2)
    implementation(libs.androidx.compose.ui.ui.graphics2)
    implementation(libs.androidx.compose.ui.ui.tooling.preview2)
    implementation(libs.androidx.compose.material3.material32)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit.v115)
    androidTestImplementation(libs.androidx.espresso.core.v351)
    androidTestImplementation(libs.androidx.compose.ui.ui.test.junit42)
    androidTestImplementation(platform(libs.androidx.compose.bom.v20240400))
    debugImplementation(libs.androidx.compose.ui.ui.tooling2)
    debugImplementation(libs.androidx.compose.ui.ui.test.manifest2)


    addFirebaseDependencies()
    addHiltDependencies()
    addNavigatorDependencies()
    addSerializationDependencies()

    addFeatureDependencies()
    addCoreDependencies()
}
