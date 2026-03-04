plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.fintrack"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fintrack"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    // ROOM
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // Lifecycle (dùng được cho Java)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.8.0")

    // UI
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // CameraX
    implementation("androidx.camera:camera-camera2:1.3.2")
    implementation("androidx.camera:camera-lifecycle:1.3.2")
    implementation("androidx.camera:camera-view:1.3.2")

    // ML Kit
    implementation("com.google.mlkit:text-recognition:16.0.0")
}