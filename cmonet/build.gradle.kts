plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.mckimquyen.cmonet"
    compileSdk = Apps.targetSdk

    defaultConfig {
        minSdk = Apps.minSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    api(libs.core.ktx)
    api(libs.appcompat)
    api(libs.material)
//    testImplementation(libs.test.junit)
//    androidTestImplementation(libs.test.ext.junit)
//    androidTestImplementation(libs.test.espresso.core)
}
