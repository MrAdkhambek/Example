import org.gradle.api.JavaVersion

object Config {
    const val compileSdkVersion = 31
    const val buildToolsVersion = "31"

    const val applicationId = "me.adkhambek.example"
    const val minSdkVersion = 21
    const val targetSdkVersion = 31
    const val versionCode = 1
    const val versionName = "1.0"
    const val multiDexEnabled = true
    const val consumerProguardFiles = "consumer-rules.pro"

    const val runnerBuilder = "runnerBuilder"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val testInstrumentationRunnerArguments = "de.mannodermaus.junit5.AndroidJUnit5Builder"

    val javaVersion = JavaVersion.VERSION_11

    val freeCompilerArgs = listOf(
        "-Xuse-experimental=androidx.compose.animation.ExperimentalAnimationApi",
        "-Xuse-experimental=kotlinx.serialization.ExperimentalSerializationApi",
        "-Xuse-experimental=kotlinx.serialization.InternalSerializationApi",
        "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-Xopt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-Xopt-in=kotlin.contracts.ExperimentalContracts",
        "-Xopt-in=kotlin.RequiresOptIn",
        "-Xjvm-default=all",
    )

    val excludes = listOf(
        "META-INF/ASL2.0",
        "META-INF/DEPENDENCIES",
        "META-INF/INDEX.LIST",
        "META-INF/LICENSE",
        "META-INF/LICENSE.txt",
        "META-INF/NOTICE",
        "META-INF/NOTICE.txt",
        "META-INF/*.kotlin_module",
        "META-INF/license.txt",
        "META-INF/notice.txt",
        "META-INF/io.netty.versions.properties",
    )
}
