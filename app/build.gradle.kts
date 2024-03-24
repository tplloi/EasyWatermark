import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = (Apps.compileSdk)
    buildToolsVersion = (Apps.buildTools)
    namespace = "com.mckimquyen.watermark"

    defaultConfig {
        applicationId = "com.mckimquyen.watermark"
        minSdk = (Apps.minSdk)
        targetSdk = (Apps.targetSdk)
        versionCode = 20240323
        versionName = "2024.03.23"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        setProperty("archivesBaseName", "$applicationId-v$versionName($versionCode)")
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
        }

        val release by getting {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "coroutines.pro", "proguard-rules.pro"
            )
        }

        create("benchmark") {
            initWith(release)
            signingConfig = signingConfigs.getByName("debug")
            // [START_EXCLUDE silent]
            // Selects release buildType if the benchmark buildType not available in other modules.
            matchingFallbacks.add("release")
            // [END_EXCLUDE]
            proguardFiles("benchmark-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    // change output apk name
    applicationVariants.all {
        outputs.all {
            (this as? BaseVariantOutputImpl)?.outputFileName =
                "$applicationId-v$versionName($versionCode).apk"
        }
    }

    packagingOptions {
        resources.excludes.add("DebugProbesKt.bin")
    }

    android.buildFeatures.viewBinding = true

    kotlinOptions {
        jvmTarget = "11"
    }

    lint {
        baseline = file("lint-baseline.xml")
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    api(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(mapOf("path" to ":cmonet")))
    api(libs.room.runtime)
    api(libs.room.ktx)
    kapt(libs.room.compiler)
    api(libs.datastore.preference)
    api(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.compiler)
    api(libs.asyncLayoutInflater)
    api(libs.glide.glide)
    kapt(libs.glide.compiler)
    api(libs.compressor)
    api(libs.kotlin.stdlib)
    api(libs.kotlin.coroutine.android)
    api(libs.kotlin.coroutine.core)
    api(libs.appcompat)
    api(libs.material)
    api(libs.fragment.ktx)
    api(libs.activity.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.lifecycle.livedata.ktx)
    api(libs.lifecycle.viewModel.ktx)
    api(libs.viewpager2)
    api(libs.recyclerview)
    api(libs.constraintLayout)
    api(libs.exifInterface)
    api(libs.palette.ktx)
    api(libs.profieinstaller)
    api(libs.colorpicker)
}
