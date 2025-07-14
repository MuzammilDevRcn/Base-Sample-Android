package com.muzammil.android.templates.koin

import android.content.Context
import androidx.startup.Initializer
import org.koin.androix.startup.KoinInitializer
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Initializes the CrashTrackerService using AndroidX App Startup.
 * This initializer depends on KoinAppInitializer to ensure Koin is ready before
 * attempting to inject the CrashTrackerService.
 */
class CrashTrackerInitializer : Initializer<Unit>, KoinComponent {

    // Inject the CrashTrackerService using Koin
    // The 'by inject()' delegate will resolve the singleton instance defined in appModule
    private val crashTrackerService: CrashTrackerService by inject()

    override fun create(context: Context) {
        // Configure the crash tracker service once it's available
        crashTrackerService.configure(context)
    }

    @OptIn(KoinExperimentalAPI::class)
    override fun dependencies(): List<Class<out Initializer<*>>> {
        // Ensure KoinInitializer runs before this initializer
        // This guarantees Koin's context is ready and services can be injected
        return listOf(KoinInitializer::class.java)
    }
}