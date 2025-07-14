package com.muzammil.android.templates.revenue.remoteConfiguration.domain.dataSource

interface RemoteConfigDataSource {
    suspend fun fetchAndActivate(callback: (Boolean) -> Unit)
    fun addConfigUpdateListener(onUpdate: () -> Unit, onError: (Exception) -> Unit)
    fun getString(key: String): String
    fun getBoolean(key: String): Boolean
    fun getLong(key: String): Long
    fun getDouble(key: String): Double
}