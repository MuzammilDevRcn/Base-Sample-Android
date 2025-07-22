import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.googleKsp)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.androidxNavigationSafeArgs)

    // PlayStore version only
    alias(libs.plugins.googleCrashlytics)
    alias(libs.plugins.googleGms)
}

android {
    namespace = "com.muzammil.android.templates"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
//        applicationId = "com.muzammil.android.templates"
        applicationId = "com.gallery.photo.galerie.galeria.foto.galerij.galeri.galleri.galery"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "CONSENT_TEST_DEVICES_IDS", "\"${project.findProperty("CONSENT_TEST_DEVICE_IDS") ?: ""}\"")
            buildConfigField("String", "CONSENT_TEST_GEOGRAPHY", "\"${project.findProperty("CONSENT_TEST_GEOGRAPHY_DEBUG") ?: "0"}\"")
            buildConfigField("String", "ADS_TEST_DEVICES_IDS", "\"${project.findProperty("ADS_TEST_DEVICES_IDS") ?: ""}\"")

            resValue("string", "admob_app_id", "ca-app-pub-3940256099942544~3347511713")
            resValue("string", "admob_interstitial_ad_unit", "ca-app-pub-3940256099942544/1033173712")
            resValue("string", "admob_native_ad_unit", "ca-app-pub-3940256099942544/2247696110")
            resValue("string", "admob_banner_ad_unit", "ca-app-pub-3940256099942544/6300978111")
            resValue("string", "admob_app_open_ad_unit", "ca-app-pub-3940256099942544/3419835294")
            resValue("string", "admob_rewarded_ad_unit", "ca-app-pub-3940256099942544/5224354917")


            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        release {

            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }


    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            freeCompilerArgs.add("-opt-in=kotlin.RequiresOptIn")
        }
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.appCompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerView)

    implementation(libs.google.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintLayout)
    implementation(libs.androidx.core.splashscreen)

    // Preferences
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.preference.ktx)

    // Lifecycle
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.extensions)
    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Firebase BOM
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.config)

    // Google Ads.
    implementation(libs.google.gms.ads)

    // Koin (Dependency injection)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.startup)
    implementation(libs.koin.core.coroutines)

    // Flexible Design
    implementation(libs.ssp.android)
    implementation(libs.sdp.android)

    // Animation
    implementation(libs.airbnb.lottie)

    // shimmer Effect
    implementation(libs.shimmer)

    // Background Thread
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Retrofit for Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor) // Optional: for logging

    // Memory Leaks
    debugImplementation(libs.leakcanary.android)

    implementation("com.github.mvojtkovszky:BillingHelper:4.0.1")

}