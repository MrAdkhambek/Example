@file:Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage"
)

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.lib) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.android.junit5) apply false
}

allprojects {

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    tasks.withType(KotlinCompile::class).all {
        kotlinOptions {
            jvmTarget = Config.javaVersion.toString()
            freeCompilerArgs = (freeCompilerArgs + Config.freeCompilerArgs).distinct()
        }
    }
}

subprojects {

    plugins.matching { it is AppPlugin || it is LibraryPlugin }.whenPluginAdded {
        configure<BaseExtension> {

            when (this) {
                is AppExtension -> {
                    packagingOptions {
                        resources.excludes.add("META-INF/*.kotlin_module")
                    }
                }
            }

            compileOptions {
                isCoreLibraryDesugaringEnabled = true
                sourceCompatibility = Config.javaVersion
                targetCompatibility = Config.javaVersion
            }

            defaultConfig {
                minSdk = Config.minSdkVersion
                targetSdk = Config.targetSdkVersion
                versionCode = Config.versionCode
                versionName = Config.versionName
                testInstrumentationRunner = Config.testInstrumentationRunner
                testInstrumentationRunnerArguments[Config.runnerBuilder] = Config.testInstrumentationRunnerArguments
            }

            packagingOptions {
                resources.excludes.addAll(Config.excludes)
            }

            dependencies(
                closureOf<DependencyHandler> {

                    add("androidTestRuntimeOnly", libs.mannodermaus.runner)
                    add("androidTestImplementation", libs.mannodermaus.core)

                    add("testRuntimeOnly", libs.jupiter.engine)
                    add("testImplementation", libs.jupiter.api)
                    add("testImplementation", libs.jupiter.params)
                    add("coreLibraryDesugaring", libs.coreLibraryDesugaring)
                }
            )

            tasks.withType<Test> {
                useJUnitPlatform()
            }
        }
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
