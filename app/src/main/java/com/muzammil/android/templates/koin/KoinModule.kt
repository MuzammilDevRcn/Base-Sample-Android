package com.muzammil.android.templates.koin

import com.muzammil.android.templates.core.di.coreDiModules
import com.muzammil.android.templates.revenue.AdsViewModel
import com.muzammil.android.templates.revenue.billing.di.billingModule
import com.muzammil.android.templates.revenue.remoteConfiguration.di.remoteConfigDiModule
import com.muzammil.android.templates.revenue.userConsent.di.userConsentDiModule
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val firebaseModule = module { single<CrashTrackerService>(createdAtStart = true) { CrashTrackerServiceImpl() } }

val revenueModule = module {
    includes(remoteConfigDiModule, userConsentDiModule, billingModule)

    viewModelOf(::AdsViewModel)
}

val myAppModules = listOf(firebaseModule, coreDiModules, revenueModule)