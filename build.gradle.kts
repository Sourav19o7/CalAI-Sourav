import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.dokka.gradle.DokkaTaskPartial


plugins {
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.dokka")
    id("com.google.firebase.crashlytics") version "3.0.2" apply false
    id("androidx.baselineprofile") version "1.3.3" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
    id("com.android.compose.screenshot") version "0.0.1-alpha08" apply false
}

val projectSource = file(projectDir)
val configFile = files("$rootDir/build/reports/detekt/detekt.yml")
val kotlinFiles = "**/*.kt"
val resourceFiles = "**/resources/**"
val buildFiles = "**/build/**"

tasks.register<Detekt>("detektAll") {
    val autoFix = project.hasProperty("detektAutoFix")

    description = "Custom DETEKT build for all modules"
    parallel = true
    ignoreFailures = false
    autoCorrect = autoFix
    buildUponDefaultConfig = true
    setSource(projectSource)
    config.setFrom(configFile)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
    }
}

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
    dokkaSourceSets {
        configureEach {
            documentedVisibilities.set(
                setOf(
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PUBLIC,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PRIVATE,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PROTECTED,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.INTERNAL,
                    org.jetbrains.dokka.DokkaConfiguration.Visibility.PACKAGE,
                )
            )
            //includeNonPublic.set(true)
            //skipDeprecated.set(true)
            //reportUndocumented.set(true)
            //noAndroidSdkLink.set(false)
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    outputDirectory.set(buildDir.resolve("dokkaMultiModuleOutput"))

}

// configure all format tasks at once
tasks.withType<DokkaTaskPartial>().configureEach {
    dokkaSourceSets.configureEach {
        documentedVisibilities.set(
            setOf(
                org.jetbrains.dokka.DokkaConfiguration.Visibility.PUBLIC,
                org.jetbrains.dokka.DokkaConfiguration.Visibility.PRIVATE,
                org.jetbrains.dokka.DokkaConfiguration.Visibility.PROTECTED,
                org.jetbrains.dokka.DokkaConfiguration.Visibility.INTERNAL,
                org.jetbrains.dokka.DokkaConfiguration.Visibility.PACKAGE,
            )
        )
    }
}


buildscript {

    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")

        }
        repositories {
            google()
            mavenCentral()
            maven {
                url = uri("https://developer.huawei.com/repo/")
            }
            maven {
                url = uri("https://jitpack.io")
            }
        }

        dependencies {
            classpath(Dependencies.HILT_AGP)
            classpath(Dependencies.GOOGLE_SERVICES)
        }
    }
}