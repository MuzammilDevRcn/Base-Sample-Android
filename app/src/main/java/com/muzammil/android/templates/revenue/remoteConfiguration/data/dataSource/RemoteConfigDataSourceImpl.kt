package com.muzammil.android.templates.revenue.remoteConfiguration.data.dataSource

import com.google.firebase.remoteconfig.ConfigUpdate
import com.google.firebase.remoteconfig.ConfigUpdateListener
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigException
import com.muzammil.android.templates.revenue.remoteConfiguration.domain.dataSource.RemoteConfigDataSource

class RemoteConfigDataSourceImpl(private val remoteConfig: FirebaseRemoteConfig) : RemoteConfigDataSource {

    override suspend fun fetchAndActivate(callback: (Boolean) -> Unit) {
        remoteConfig.fetchAndActivate().addOnCompleteListener {
            callback.invoke(it.isSuccessful)
        }
    }

    override fun addConfigUpdateListener(onUpdate: () -> Unit, onError: (Exception) -> Unit) {
        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
            override fun onUpdate(configUpdate: ConfigUpdate) {
                remoteConfig.activate().addOnSuccessListener {
                    onUpdate()
                }
            }

            override fun onError(error: FirebaseRemoteConfigException) {
                onError(error)
            }
        })
    }

    override fun getString(key: String): String = remoteConfig.getString(key)

    override fun getBoolean(key: String): Boolean = remoteConfig.getBoolean(key)

    override fun getLong(key: String): Long = remoteConfig.getLong(key)

    override fun getDouble(key: String): Double = remoteConfig.getString(key).toDouble()
}