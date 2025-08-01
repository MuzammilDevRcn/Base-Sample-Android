// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.googleKsp) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidxNavigationSafeArgs) apply false

    // PlayStore version only
    alias(libs.plugins.googleCrashlytics) apply false
    alias(libs.plugins.googleGms) apply false
}