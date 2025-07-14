package com.muzammil.android.templates.revenue.ads.data.repository

import android.app.Activity
import android.content.Context
import androidx.annotation.MainThread
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.muzammil.android.templates.revenue.ads.domain.repository.IAdsSdk

class GoogleAdsSdk() : IAdsSdk {

    private var interstitialAd: InterstitialAd? = null

    @MainThread
    override fun initializeSdk(context: Context, onComplete: () -> Unit) {
        MobileAds.initialize(context) { onComplete() }
    }

    @MainThread
    override fun setTestDevices(deviceIds: List<String>) {
        MobileAds.setRequestConfiguration(RequestConfiguration.Builder().setTestDeviceIds(deviceIds).build())
    }

    @MainThread
    override fun loadInterstitialAd(context: Context, interAdId: String, onLoaded: () -> Unit, onError: (code: Int, message: String) -> Unit) {
        InterstitialAd.load(
            context,
            interAdId,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    onLoaded()
                }

                override fun onAdFailedToLoad(adError: LoadAdError): Unit = onError.invoke(adError.code, adError.message)
            })
    }

    @MainThread
    override fun showInterstitialAd(activity: Activity, onShow: () -> Unit, onDismiss: (impression: Boolean) -> Unit, onError: (code: Int, message: String) -> Unit) {
        val ad = interstitialAd ?: return

        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            var impression = false

            override fun onAdImpression() {
                impression = true
            }

            override fun onAdShowedFullScreenContent(): Unit = onShow()
            override fun onAdDismissedFullScreenContent(): Unit = onDismiss(impression)
            override fun onAdFailedToShowFullScreenContent(adError: AdError): Unit = onError(adError.code, adError.message)
        }
        ad.show(activity)
        interstitialAd = null
    }
}