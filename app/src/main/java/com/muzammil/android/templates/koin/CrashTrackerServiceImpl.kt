package com.muzammil.android.templates.koin

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

private const val TAG = "FirebaseCrashTracker"

class CrashTrackerServiceImpl : CrashTrackerService {

    private var isConfigured = false
    private lateinit var crashlytics: FirebaseCrashlytics

    override fun configure(context: Context) {
        if (!isConfigured) {
            Log.d(TAG, "Configuring CrashTrackerService with context: ${context.applicationContext.packageName}")
            // Initialize FirebaseApp if not already initialized (though App Startup usually handles this)
            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            }
            crashlytics = FirebaseCrashlytics.getInstance()
            // Optional: Enable/disable crash collection based on build type or user consent
            // crashlytics.setCrashlyticsCollectionEnabled(BuildConfig.DEBUG) // Example [12]
            isConfigured = true
            Log.d(TAG, "Firebase Crashlytics configured.")
        } else {
            Log.w(TAG, "CrashTrackerService already configured.")
        }
    }

    override fun recordException(throwable: Throwable, message: String?) {
        if (isConfigured) {
            message?.let {
                crashlytics.log("Custom Log: $it") // Log custom message [13]
                Log.e(TAG, "Recording exception with message: $it", throwable)
            }?: run {
                Log.e(TAG, "Recording exception: ${throwable.message}", throwable)
            }
            crashlytics.recordException(throwable) // Record the exception [13, 14]
        } else {
            Log.e(TAG, "CrashTrackerService not configured. Cannot record exception.", throwable)
        }
    }

    override fun setUserId(userId: String) {
        if (isConfigured) {
            crashlytics.setUserId(userId) // Set user identifier [12, 13]
            Log.d(TAG, "User ID set for Crashlytics: $userId")
        } else {
            Log.w(TAG, "CrashTrackerService not configured. Cannot set user ID.")
        }
    }

    override fun log(message: String) {
        if (isConfigured) {
            crashlytics.log(message) // Add custom log message [13]
            Log.d(TAG, "Crashlytics Log: $message")
        } else {
            Log.w(TAG, "CrashTrackerService not configured. Cannot log message.")
        }
    }

    override fun setCustomKey(key: String, value: String) {
        if (isConfigured) {
            crashlytics.setCustomKey(key, value) // Set custom key-value pair [13, 15, 16]
            Log.d(TAG, "Custom key set for Crashlytics: $key = $value")
        } else {
            Log.w(TAG, "CrashTrackerService not configured. Cannot set custom key.")
        }
    }
}