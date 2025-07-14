package com.muzammil.android.templates.revenue.ads.di

import com.muzammil.android.templates.revenue.ads.data.dataSource.InterstitialAdsDataSource
import com.muzammil.android.templates.revenue.ads.data.repository.GoogleAdsSdk
import com.muzammil.android.templates.revenue.ads.domain.repository.IAdsSdk
import org.koin.dsl.module

val admobAdsModule = module {

    single<IAdsSdk> { GoogleAdsSdk() }

    // Interstitial Ads
    single { InterstitialAdsDataSource(context = get(), dispatcherProvider = get(), sharedPreferenceUtils = get(), internetManager = get(), adsSdk = get()) }

}