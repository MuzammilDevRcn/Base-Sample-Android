package com.muzammil.android.templates.revenue

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muzammil.android.templates.revenue.remoteConfiguration.domain.repository.RemoteConfigRepository
import com.muzammil.android.templates.revenue.remoteConfiguration.presentation.mvi.RemoteConfigIntent
import com.muzammil.android.templates.revenue.remoteConfiguration.presentation.mvi.RemoteConfigurationState
import com.muzammil.android.templates.revenue.userConsent.domain.model.UserConsentState
import com.muzammil.android.templates.revenue.userConsent.domain.repository.UserConsentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class AdsViewModel(
    val remoteConfigRepository: RemoteConfigRepository,
    val userConsentRepository: UserConsentRepository
) : ViewModel() {

    init {
        Log.i(TAG, "AdsViewModel initiated")
    }

    private val _remoteConfigurationState = MutableStateFlow<RemoteConfigurationState>(RemoteConfigurationState.Idle)
    val remoteConfigurationState: StateFlow<RemoteConfigurationState> = _remoteConfigurationState

    val userConsentState: StateFlow<UserConsentState> =
        userConsentRepository.userConsentState.stateIn(viewModelScope, SharingStarted.Eagerly, UserConsentState.UNKNOWN)

    fun requestUserConsentIfNeeded(activity: Activity) {
        userConsentRepository.startUserConsentRequestUiFlowIfNeeded(activity)
    }

    fun processIntent(intent: RemoteConfigIntent) {
        when (intent) {
            RemoteConfigIntent.Fetch -> fetchRemoteConfiguration()
        }
    }

    private fun fetchRemoteConfiguration() = viewModelScope.launch {
        _remoteConfigurationState.value = RemoteConfigurationState.Loading
        try {
            remoteConfigRepository.checkRemoteConfig { success, msg ->
                _remoteConfigurationState.value = (if (success) RemoteConfigurationState.Success(System.currentTimeMillis())
                else RemoteConfigurationState.Error(msg))
            }
        } catch (e: Exception) {
            _remoteConfigurationState.value = RemoteConfigurationState.Error(e.message ?: "Unknown error")
        }
    }
}

private const val TAG = "AdsViewModelLogsInfo"