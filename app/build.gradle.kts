import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-parcelize")
    id("org.jetbrains.kotlin.kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 34
    buildToolsVersion = "34.0.0"
    namespace = "com.mckimquyen.watermark"

    defaultConfig {
        applicationId = "com.mckimquyen.watermark"
        minSdk = 23
        targetSdk = 34
        versionCode = 20250204
        versionName = "2025.02.04"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        setProperty("archivesBaseName", "$applicationId-v$versionName($versionCode)")
    }

    signingConfigs {
        create("release") {
            keyAlias = "loi"
            keyPassword = "04021993"
            storeFile = file("keystore.jks")
            storePassword = "04021993"
        }
    }
    flavorDimensions.add("default")
    productFlavors {
        create("appTest") {
//            dimension = "version"
//            applicationIdSuffix = ".free"
//            versionNameSuffix = "-free"
//            resValue("string", "app_name", "Watermark Creator Free")
            resValue("string", "SDK_KEY", "e75FnQfS9XTTqM1Kne69U7PW_MBgAnGQTFvtwVVui6kRPKs5L7ws9twr5IQWwVfzPKZ5pF2IfDa7lguMgGlCyt")
            resValue("string", "BANNER", "d3455cc529985b25")
            resValue("string", "INTER", "a48241ebcb20ad5c")
            resValue("string", "EnableAdInter", "true")
            resValue("string", "EnableAdBanner", "true")
        }
        create("appRelease") {
//            dimension = "version"
//            applicationIdSuffix = ".free"
//            versionNameSuffix = "-free"
//            resValue("string", "app_name", "Watermark Creator")
            resValue("string", "SDK_KEY", "e75FnQfS9XTTqM1Kne69U7PW_MBgAnGQTFvtwVVui6kRPKs5L7ws9twr5IQWwVfzPKZ5pF2IfDa7lguMgGlCyt")
            resValue("string", "BANNER", "d3455cc529985b25")
            resValue("string", "INTER", "a48241ebcb20ad5c")
            resValue("string", "EnableAdInter", "true")
            resValue("string", "EnableAdBanner", "true")
        }
    }

    buildTypes {
//        val debug by getting {
//            applicationIdSuffix = ".debug"
//        }

        val release by getting {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "coroutines.pro", "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

//        create("benchmark") {
//            initWith(release)
//            signingConfig = signingConfigs.getByName("debug")
//            // [START_EXCLUDE silent]
//            // Selects release buildType if the benchmark buildType not available in other modules.
//            matchingFallbacks.add("release")
//            // [END_EXCLUDE]
//            proguardFiles("benchmark-rules.pro")
//        }
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
    api("com.applovin:applovin-sdk:13.0.1")
    api("com.jakewharton:process-phoenix:3.0.0")
    implementation("com.google.android.play:review:2.0.2")
    implementation("com.google.android.play:review-ktx:2.0.2")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.14")
}
