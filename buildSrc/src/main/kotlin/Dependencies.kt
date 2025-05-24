import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.project

object Dependencies {

    // Compose Dependencies
    const val COMPOSE_MATERIAL = "androidx.compose.material3:material3:${Versions.COMPOSE_MATERIAL3}"
    const val COMPOSE_WINDOW_SIZE_CLASS = "androidx.compose.material3:material3-window-size-class:${Versions.COMPOSE_MATERIAL3}"
    const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val COMPOSE_UI_GRAPHICS = "androidx.compose.ui:ui-graphics:${Versions.COMPOSE}"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
    const val COMPOSE_UI_TOOLING_PREVIEW = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
    const val COMPOSE_RUNTIME = "androidx.compose.runtime:runtime:${Versions.COMPOSE}"
    const val COMPOSE_NAVIGATION = "androidx.navigation:navigation-compose:${Versions.COMPOSE_NAVIGATION}"
    const val COMPOSE_UI_TESTS = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
    const val COMPOSE_ANDROID_TEST = "androidx.compose.ui:ui-test-manifest:${Versions.COMPOSE}"

    // Dependency Injection (Hilt)
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.HILT}"
    const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT}"
    const val HILT_AGP = "com.google.dagger:hilt-android-gradle-plugin:${Versions.HILT}"
    const val HILT_ANDROID_TEST = "com.google.dagger:hilt-android-testing:${Versions.HILT}"
    const val HILT_COMPOSE = "androidx.hilt:hilt-navigation-compose:${Versions.HILT_NAVIGATION_COMPOSE}"
    const val HILT_WORK_COMPILER = "androidx.hilt:hilt-compiler:${Versions.HILT_WORK_COMPILER}"

    // Google Services
    const val GOOGLE_SERVICES = "com.google.gms:google-services:${Versions.PLAY_SERVICE}"
    const val GOOGLE_PLAY_SERVICES = "com.google.android.gms:play-services-base:${Versions.PLAY_SERVICE_BASE}"
    const val GOOGLE_PLAY_ADS = "com.google.android.gms:play-services-ads-identifier:${Versions.PLAY_SERVICE_ADS}"


    // Networking (Retrofit & OkHttp)
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP}"
    const val OKHTTP_LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP}"
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"

    // Firebase
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics"
    const val FIREBASE_AUTH = "com.google.firebase:firebase-auth"
    const val FIREBASE_DYNAMIC_LINKS = "com.google.firebase:firebase-dynamic-links"
    const val FIREBASE_BOM = "com.google.firebase:firebase-bom:${Versions.FIREBASE_BOM}"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics"
    const val FIREBASE_FIRE_STORE = "com.google.firebase:firebase-firestore"
    const val FIREBASE_MESSAGING = "com.google.firebase:firebase-inappmessaging-display"
    const val FIREBASE_PERFORMANCE = "com.google.firebase:firebase-perf"

    // Room (Database)
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"

    // Testing Dependencies
    const val JUPITER_JUNIT = "org.junit.jupiter:junit-jupiter-api:${Versions.JUPITER_JUNIT}"
    const val JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:${Versions.JUPITER_JUNIT}"
    const val MOCKK = "io.mockk:mockk:${Versions.MOCKK}"
    const val MOCKK_ANDROID = "io.mockk:mockk-android:${Versions.MOCKK}"
    const val MOCKK_AGENT = "io.mockk:mockk-agent:${Versions.MOCKK}"
    const val ROBOELECTRIC = "org.robolectric:robolectric:${Versions.ROBO_ELECTRIC}"
    const val KOTEST_RUNNER = "io.kotest:kotest-runner-junit5:${Versions.KOTEST_RUNNER}"
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core:${Versions.KOTEST_RUNNER}"
    const val KOTEST_PROPERTY = "io.kotest:kotest-property:${Versions.KOTEST_RUNNER}"
    const val COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.KOTLIN_COROUTINES_TEST}"
    const val ANDROIDX_TEST_CORE = "androidx.test:core:${Versions.TEST_CORE}"

    // Serialization
    const val GSON = "com.google.code.gson:gson:${Versions.GSON}"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.KOTLIN_SERIALIZATION}"

    // UI Libraries (Lottie, Glide)
    const val LOTTIE_COMPOSE = "com.airbnb.android:lottie-compose:${Versions.LOTTIE_COMPOSE}"
    const val GLIDE_COMPOSE = "com.github.bumptech.glide:compose:${Versions.GLIDE}"

    // Image Libraries
    val COIL = "io.coil-kt:coil-compose:${Versions.COIL}"
    const val COIL_GIF = "io.coil-kt:coil-gif:${Versions.COIL_GIF}"

    // Others
    const val PALETTE = "androidx.palette:palette-ktx:${Versions.PALETTE}"
    const val JWT_TOKEN = "io.jsonwebtoken:jjwt-api:${Versions.JWT_TOKEN}"
    const val REVIEW_FACTORY = "com.google.android.play:review-ktx:${Versions.REVIEW_FACTORY}"
    const val APP_UPDATE = "com.google.android.play:app-update:${Versions.APP_UPDATE}"
    const val APP_UPDATE_KTX = "com.google.android.play:app-update-ktx:${Versions.APP_UPDATE}"
    const val DATASTORE_PROTO = "androidx.datastore:datastore:${Versions.PROTO_DATA_STORE}"
    const val DATASTORE_PREFERENCES = "androidx.datastore:datastore-preferences:${Versions.PROTO_DATA_STORE}"
    const val WORK_RUNTIME = "androidx.work:work-runtime:${Versions.COROUTINE_WORKER}"
    const val HILT_WORK = "androidx.hilt:hilt-work:${Versions.HILT_WORK_COMPILER}"
    const val GOOGLE_AUTH = "com.google.android.gms:play-services-auth:${Versions.GOOGLE_AUTH}"
    const val HEALTH_CONNECT = "androidx.health.connect:connect-client:${Versions.HEALTH_CONNECT}"
    const val IMMUTABLE_COLLECTIONS =
        "org.jetbrains.kotlinx:kotlinx-collections-immutable:${Versions.KOTLIN_IMMUTABLE_COLLECTIONS}"
    const val KOTLIN_ASYNC_TASK_API =
        "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.KOTLIN_ASYNC_TASK}"
    const val KOTLIN_DATETIME = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.KOTLIN_DATE_TIME}"
    const val GUAVA = "com.google.guava:guava:${Versions.GUAVA_ANDROID}"
    const val GUAVA_JRE = "com.google.guava:guava:${Versions.GUAVA_JRE}"
    const val GUAVA_LISTENER_CONFLICT = "com.google.guava:listenablefuture:9999.0-empty-to-avoid-conflict-with-guava"
}

object AppDependencies {

    // Main Modules
    const val NAVIGATOR = ":main-navigator"

    // Feature Modules
    const val ONBOARDING = ":feature-onboarding"

    // Resource Modules
    const val RESOURCES = ":main-resources"

    // Core Modules
    const val CORE = ":core"
}

// UI Dependencies
fun DependencyHandler.addComposeDependencies() {
    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_UI_GRAPHICS)
    implementation(Dependencies.COMPOSE_UI_TOOLING)
    implementation(Dependencies.COMPOSE_UI_TOOLING_PREVIEW)
    implementation(Dependencies.COMPOSE_RUNTIME)
    implementation(Dependencies.COMPOSE_NAVIGATION)
    implementation(Dependencies.COMPOSE_WINDOW_SIZE_CLASS)
    implementation(Dependencies.LOTTIE_COMPOSE)
    implementation(Dependencies.GLIDE_COMPOSE)
}

// Hilt Dependencies
fun DependencyHandler.addHiltDependencies() {
    implementation(Dependencies.HILT_ANDROID)
    implementation(Dependencies.HILT_COMPOSE)
    androidTest(Dependencies.HILT_ANDROID_TEST)
    ksp(Dependencies.HILT_COMPILER)
    kspAndroidTest(Dependencies.HILT_ANDROID_TEST)
//    ksp(Dependencies.HILT_WORK_COMPILER)
//    implementation(Dependencies.HILT_WORK)
}

fun DependencyHandler.addImageDependencies() {
    implementation(Dependencies.COIL)
    implementation(Dependencies.COIL_GIF)
}
// Navigator Dependencies
fun DependencyHandler.addNavigatorDependencies() {
    implementation(project(AppDependencies.NAVIGATOR))
}

// Networking Dependencies
fun DependencyHandler.addNetworkingDependencies() {
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.GSON_CONVERTER)
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_LOGGING_INTERCEPTOR)
    implementation(Dependencies.JWT_TOKEN)
}

fun DependencyHandler.addFirebaseDependencies() {
    implementation(platform(Dependencies.FIREBASE_BOM))
    implementation(Dependencies.FIREBASE_AUTH)
    implementation(Dependencies.FIREBASE_CRASHLYTICS)
//    implementation(Dependencies.FIREBASE_DYNAMIC_LINKS)
//    implementation(Dependencies.FIREBASE_ANALYTICS)
//    implementation(Dependencies.FIREBASE_FIRE_STORE)
//    implementation(Dependencies.FIREBASE_MESSAGING)
    implementation(Dependencies.FIREBASE_PERFORMANCE)
}

// Room and DataStore Dependencies
fun DependencyHandler.addRoomAndDataStoreDependencies() {
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_KTX)
    ksp(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.DATASTORE_PROTO)
    implementation(Dependencies.DATASTORE_PREFERENCES)
}

// Google Dependencies
fun DependencyHandler.addGoogleAuthDependencies() {
    implementation(Dependencies.GOOGLE_AUTH)
    implementation(Dependencies.REVIEW_FACTORY)
    implementation(Dependencies.APP_UPDATE)
    implementation(Dependencies.APP_UPDATE_KTX)
    implementation(Dependencies.HEALTH_CONNECT)
}

// Utility Dependencies
fun DependencyHandler.addUtilityDependencies() {
    implementation(Dependencies.PALETTE)
    implementation(Dependencies.GSON)
    implementation(Dependencies.IMMUTABLE_COLLECTIONS)
    implementation(Dependencies.GUAVA)
    implementation(Dependencies.GUAVA_JRE)
    implementation(Dependencies.GUAVA_LISTENER_CONFLICT)
}

// Testing Dependencies
fun DependencyHandler.addTestDependencies() {
    testImpl(Dependencies.JUPITER_JUNIT)
    testImpl(Dependencies.JUPITER_ENGINE)
    testImpl(Dependencies.COROUTINES_TEST)
    testImpl(Dependencies.MOCKK)
    testImpl(Dependencies.KOTEST_RUNNER)
    testImpl(Dependencies.KOTEST_ASSERTIONS)
    testImpl(Dependencies.KOTEST_PROPERTY)
    testImpl(Dependencies.ROBOELECTRIC)
    testImpl(Dependencies.ANDROIDX_TEST_CORE)
}

// Android Test Dependencies
fun DependencyHandler.addAndroidTestDependencies() {
    androidTest(Dependencies.COMPOSE_UI_TESTS)
    androidTest(Dependencies.COMPOSE_ANDROID_TEST)
    androidTest(Dependencies.MOCKK_ANDROID)
    androidTest(Dependencies.HILT_ANDROID_TEST)
    kspAndroidTest(Dependencies.HILT_COMPILER)
}

// Coroutine Worker Dependencies
fun DependencyHandler.addCoroutineWorkerDependencies() {
    implementation(Dependencies.WORK_RUNTIME)
    implementation(Dependencies.HILT_WORK)
}

// Serialization Dependencies
fun DependencyHandler.addSerializationDependencies() {
    implementation(Dependencies.KOTLIN_SERIALIZATION)
}

// Async Task Dependencies
fun DependencyHandler.addAsyncTaskDependencies() {
    implementation(Dependencies.KOTLIN_ASYNC_TASK_API)
    implementation(Dependencies.KOTLIN_DATETIME)
}

// Miscellaneous Dependencies
fun DependencyHandler.addMiscDependencies() {
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.GSON_CONVERTER)
    implementation(Dependencies.JWT_TOKEN)
    implementation(Dependencies.LOTTIE_COMPOSE)
}


// Feature Dependencies
fun DependencyHandler.addFeatureDependencies() {
    implementation(project(AppDependencies.ONBOARDING))
}

// Resource Dependencies
fun DependencyHandler.addResourceDependencies() {
    implementation(project(AppDependencies.RESOURCES))
}

// Core Dependencies
fun DependencyHandler.addCoreDependencies() {
    implementation(project(AppDependencies.CORE))
}