plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

kotlin {
    jvmToolchain(17)
}

android {
    namespace  = "id.novian.teravin_challenge"
    compileSdk = 33

    defaultConfig {
        applicationId = "id.novian.teravin_challenge"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
//    kotlinOptions {
//        jvmTarget = '1.8'
//    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    // Core
    implementation(Dependency.Core.coreKtx)
    implementation(Dependency.Core.appCompat)
    implementation(Dependency.Core.navCompKtx)
    implementation(Dependency.Core.coreNavComp)

    // Layout
    implementation(Dependency.Core.androidMaterial)
    implementation(Dependency.Core.constraintLayout)
    implementation(Dependency.Core.coordinatorLayout)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.5.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Testing
    testImplementation(Dependency.UnitTest.junit)
    androidTestImplementation(Dependency.AndroidTest.junit)
    androidTestImplementation(Dependency.AndroidTest.espresso)

    // Room
    implementation(Dependency.Room.roomKtx)
    implementation(Dependency.Room.roomRuntime)
    implementation(Dependency.Room.roomRx)
    kapt(Dependency.Room.roomCompiler)

    // Retrofit & OkHttp
    implementation(Dependency.Retrofit.retrofit)
    implementation(Dependency.Retrofit.gsonConverter)
    implementation(Dependency.Retrofit.gson)
    implementation(Dependency.OkHttp.okhttp)
    implementation(Dependency.OkHttp.interceptor)

    // ViewModel
    implementation(Dependency.ViewModel.viewModelKtx)
    implementation(Dependency.ViewModel.liveData)

    // Rx
    implementation(Dependency.RxJava.rxJava)
    implementation(Dependency.RxJava.rxAndroid)
    implementation(Dependency.RxJava.rxRetrofitAdapter)

    // Hilt
    implementation(Dependency.Hilt.hilt)
    kapt(Dependency.Hilt.compiler)
}