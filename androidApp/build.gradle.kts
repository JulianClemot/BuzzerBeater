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
        kotlinCompilerExtensionVersion = libs.versions.composeAndroidCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }

    kotlinOptions {
        jvmTarget = "19"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.core)
    implementation(libs.coroutines)
    implementation(libs.timber)
    implementation(libs.navigation)
    implementation(libs.lifecycle)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.diAndroid)

    androidTestImplementation(libs.bundles.uiTesting)
    androidTestImplementation(libs.bundles.composeTesting)

    debugImplementation(libs.bundles.composeDebug)

    testImplementation(libs.bundles.unitTesting)
}