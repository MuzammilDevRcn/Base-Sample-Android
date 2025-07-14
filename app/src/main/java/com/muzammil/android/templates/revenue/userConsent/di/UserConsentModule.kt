package com.muzammil.android.templates.revenue.userConsent.di

import com.muzammil.android.templates.revenue.userConsent.data.dataSource.UserConsentDataSource
import com.muzammil.android.templates.revenue.userConsent.data.repository.UserConsentRepositoryImpl
import com.muzammil.android.templates.revenue.userConsent.domain.repository.UserConsentRepository
import com.muzammil.android.templates.revenue.userConsent.presentation.UserConsentViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val userConsentDiModule = module {
    single { UserConsentDataSource() }

    single<UserConsentRepository> {
        UserConsentRepositoryImpl(get())
    }

    viewModel { UserConsentViewModel(get()) }
}