package com.muzammil.android.templates

import android.app.Application
import com.muzammil.android.templates.koin.myAppModules
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.koinConfiguration

@OptIn(KoinExperimentalAPI::class)
class MainApplication : Application(), KoinStartup {

    override fun onKoinStartup() = koinConfiguration {
        androidContext(this@MainApplication)
        modules(myAppModules)
    }


    override fun onCreate() {
        super.onCreate()

    }

}