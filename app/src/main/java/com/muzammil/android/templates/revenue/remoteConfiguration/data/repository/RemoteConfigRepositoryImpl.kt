package com.muzammil.android.templates.revenue.remoteConfiguration.data.repository

import android.util.Log
import com.muzammil.android.templates.core.manager.InternetManager
import com.muzammil.android.templates.core.manager.SharedPreferenceUtils
import com.muzammil.android.templates.core.utils.Constants.TAG_REMOTE
import com.muzammil.android.templates.revenue.remoteConfiguration.domain.dataSource.RemoteConfigDataSource
import com.muzammil.android.templates.revenue.remoteConfiguration.domain.repository.RemoteConfigRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RemoteConfigRepositoryImpl(
    private val remoteConfigDataSource: RemoteConfigDataSource,
    private val internetManager: InternetManager,
    private val sharedPreferenceUtils: SharedPreferenceUtils
) : RemoteConfigRepository {

    override fun checkRemoteConfig(fetchCallback: (Boolean, String) -> Unit) {
        if (internetManager.isInternetConnected.not()) {
            Log.e(TAG_REMOTE, "RemoteConfiguration: checkRemoteConfig: No Internet Found")
            fetchCallback.invoke(false, "No Internet Found")
            return
        }

        Log.i(TAG_REMOTE, "checkRemoteConfig internetConnected: true")

        CoroutineScope(Dispatchers.IO).launch {
            remoteConfigDataSource.fetchAndActivate { result ->
                if (result) {
                    Log.i(TAG_REMOTE, "Remote fetched successfully")
                    updateSharePreferences()
                    fetchCallback.invoke(true, "Config Fetched and Updated")
                } else {
                    Log.e(TAG_REMOTE, "checkRemoteConfig: Error while fetching remote values")
                    fetchCallback.invoke(false, "Error while fetching values")
                }
            }
        }

        remoteConfigDataSource.addConfigUpdateListener(
            onUpdate = {
                Log.d(TAG_REMOTE, "RemoteConfiguration: Config updated successfully")
                updateSharePreferences()
            },
            onError = { error -> Log.e(TAG_REMOTE, "RemoteConfiguration: Error updating config", error) }
        )
    }


    private fun updateSharePreferences() {

        sharedPreferenceUtils.apply {
            Log.i(TAG, "Update share preference here if needed...")
        }
        updateRemoteValuesLogsInformation()
    }

    private fun updateRemoteValuesLogsInformation() {
        sharedPreferenceUtils.apply {
            Log.i(TAG, "fetched values in logs")
        }
    }
}

private const val TAG = "RemoteConfigRepositoryLogs"