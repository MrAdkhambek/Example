@file:Suppress(
    "DSL_SCOPE_VIOLATION",
    "UnstableApiUsage"
)

plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)

    alias(libs.plugins.ktlint)
}

android {
    compileSdk = Config.compileSdkVersion
    buildToolsVersion = Config.buildToolsVersion

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName
        multiDexEnabled = Config.multiDexEnabled
        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = Config.javaVersion
        targetCompatibility = Config.javaVersion
    }

    kotlinOptions {
        jvmTarget = Config.javaVersion.toString()
        freeCompilerArgs = (freeCompilerArgs + Config.freeCompilerArgs).distinct()
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    buildFeatures {
        compose = true
        buildConfig = true

        // Disable unused AGP features
        aidl = false
        renderScript = false
        resValues = false
        shaders = false
    }
}

kapt {
    correctErrorTypes = true
    generateStubs = true
}

// ktlint
ktlint {
    android.set(true)
    verbose.set(true)
    outputColorName.set("RED")
    disabledRules.addAll("import-ordering")
}

dependencies {
    implementation(libs.multidex)

    implementation(libs.coreKtx)
    implementation(libs.bundles.kotlin)
    implementation(libs.activity.compose)
    implementation(libs.bundles.lifecycle)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    debugImplementation(libs.leakcanary)
    implementation(libs.timber)

    // Compose
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.tooling)
    runtimeOnly(libs.compose.tooling.preview)
    androidTestImplementation(libs.compose.test)
    androidTestImplementation(libs.compose.ui.test)
}
