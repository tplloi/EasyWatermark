import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("com.android.test")
    id("kotlin-android")
}

// [START macrobenchmark_setup_android]
android {
    // [START_EXCLUDE]
    compileSdk = 33
    namespace = "com.mckimquyen.macrobenchmark"

    defaultConfig {
        minSdk = Apps.minSdk
        targetSdk = Apps.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        managedDevices {
            devices {
                create<ManagedVirtualDevice>("pixel6Api31") {
                    device = "Pixel 6"
                    apiLevel = 31
                    systemImageSource = "aosp"
                }
            }
        }
    }
    // [END_EXCLUDE]
    // Note that your module name may have different name
    targetProjectPath = ":app"
    // Enable the benchmark to run separately from the app process
    experimentalProperties["android.experimental.self-instrumenting"] = true

    buildTypes {
        // declare a build type to match the target app"s build type
        create("benchmark") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            // [START_EXCLUDE silent]
            // Selects release buildType if the benchmark buildType not available in other modules.
            matchingFallbacks.add("release")
            // [END_EXCLUDE]
        }
    }
}
// [END macrobenchmark_setup_android]

// [START macrobenchmark_setup_variant]
androidComponents {
    beforeVariants(selector().all()) {
        // enable only the benchmark buildType, since we only want to measure close to release performance
        it.enable = it.buildType == "benchmark"
    }
}
// [END macrobenchmark_setup_variant]

dependencies {
    api(project(":baseBenchmarks"))
    api(libs.benchmark)
//    api(libs.test.ext.junit)
//    api(libs.test.espresso.core)
//    api(libs.test.uiautomator)
    api(libs.kotlin.stdlib)
}
