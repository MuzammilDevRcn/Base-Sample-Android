package com.muzammil.android.templates.revenue.remoteConfiguration.domain.repository

interface RemoteConfigRepository {
    fun checkRemoteConfig(fetchCallback: (Boolean, String) -> Unit)
}
