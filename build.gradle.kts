@file:Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage"
)

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektCreateBaselineTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
// import com.android.build.gradle.AppExtension
// import com.android.build.gradle.LibraryExtension
// import com.android.build.gradle.AppPlugin
// import com.android.build.gradle.BaseExtension
// import com.android.build.gradle.LibraryPlugin

plugins {
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.lib) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false

    alias(libs.plugins.ktlint) apply true
    alias(libs.plugins.detekt) apply true
}

allprojects {

    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }

    detekt {
        config = files("$rootDir/config/detekt/detekt.yml")
        baseline = file("$rootDir/config/detekt/baseline.xml")
        buildUponDefaultConfig = true
    }

    tasks.withType(KotlinCompile::class).all {
        kotlinOptions {
            jvmTarget = Config.javaVersion.toString()
            freeCompilerArgs = (freeCompilerArgs + Config.freeCompilerArgs).distinct()
        }
    }

    tasks.withType<Detekt>().configureEach {
        this.jvmTarget = Config.javaVersion.toString()
        reports {
            xml.required.set(true)
            html.required.set(true)
            txt.required.set(true)
            sarif.required.set(true)
        }
    }
    tasks.withType<DetektCreateBaselineTask>().configureEach {
        this.jvmTarget = Config.javaVersion.toString()
    }
}

// subprojects {
//
//    plugins.matching { it is AppPlugin || it is LibraryPlugin }.whenPluginAdded {
//        configure<BaseExtension> {
//
//            when (this) {
//                is AppExtension -> {
//
//                }
//            }
//
//            dependencies(
//                closureOf<DependencyHandler> {
//                    add("coreLibraryDesugaring", "com.android.tools:desugar_jdk_libs:1.1.5")
//                }
//            )
//        }
//    }
// }

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
