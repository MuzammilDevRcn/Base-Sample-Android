package com.muzammil.android.templates.revenue.remoteConfiguration.di

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.muzammil.android.templates.R
import com.muzammil.android.templates.revenue.remoteConfiguration.data.dataSource.RemoteConfigDataSourceImpl
import com.muzammil.android.templates.revenue.remoteConfiguration.data.provider.DefaultDispatcherProvider
import com.muzammil.android.templates.revenue.remoteConfiguration.data.repository.RemoteConfigRepositoryImpl
import com.muzammil.android.templates.revenue.remoteConfiguration.domain.dataSource.RemoteConfigDataSource
import com.muzammil.android.templates.revenue.remoteConfiguration.domain.provider.DispatcherProvider
import com.muzammil.android.templates.revenue.remoteConfiguration.domain.repository.RemoteConfigRepository
import org.koin.dsl.module

val remoteConfigDiModule = module {
    single {
        Firebase.remoteConfig.apply {
            val configSettings = remoteConfigSettings {
                fetchTimeoutInSeconds = 10
                minimumFetchIntervalInSeconds = 0L
            }
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(R.xml.remote_config_defaults)
        }
    }

    single<DispatcherProvider> { DefaultDispatcherProvider() }

    single<RemoteConfigDataSource> { RemoteConfigDataSourceImpl(remoteConfig = get()) }

    single<RemoteConfigRepository> { RemoteConfigRepositoryImpl(get(), get(), get()) }
}