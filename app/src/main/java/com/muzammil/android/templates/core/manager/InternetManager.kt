package com.muzammil.android.templates.core.manager

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.muzammil.android.templates.core.firebase.FirebaseUtils.recordException
import javax.inject.Inject


class InternetManager @Inject constructor(private val connectivityManager: ConnectivityManager) {

    val isInternetConnected: Boolean
        get() {
            try {
                val network = connectivityManager.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                return when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
                    else -> false
                }
            } catch (ex: Exception) {
                ex.recordException("Internet Manager")
                return false
            }
        }
}