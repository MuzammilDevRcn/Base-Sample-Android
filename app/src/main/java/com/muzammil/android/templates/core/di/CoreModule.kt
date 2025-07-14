package com.muzammil.android.templates.core.di

import android.content.Context
import android.net.ConnectivityManager
import com.muzammil.android.templates.core.manager.InternetManager
import com.muzammil.android.templates.core.manager.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val coreDiModules = module {
    single { InternetManager(connectivityManager = androidContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager) }

    single<SharedPreferenceUtils> { SharedPreferenceUtils(context = androidContext()) }
}