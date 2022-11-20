plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.julian.buzzerbeater.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.julian.buzzerbeater.android"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.core)
    implementation(libs.timber)
    implementation(libs.navigation)
    implementation(libs.lifecycle)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.di)

    androidTestImplementation(libs.bundles.uiTesting)
    androidTestImplementation(libs.bundles.composeTesting)

    debugImplementation(libs.bundles.composeDebug)

    testImplementation(libs.bundles.unitTesting)
}