package com.muzammil.android.templates.revenue.remoteConfiguration.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muzammil.android.templates.revenue.remoteConfiguration.presentation.mvi.RemoteConfigIntent
import com.muzammil.android.templates.revenue.remoteConfiguration.domain.repository.RemoteConfigRepository
import com.muzammil.android.templates.revenue.remoteConfiguration.presentation.mvi.RemoteConfigurationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class RemoteConfigurationViewModel(private val remoteConfigRepository: RemoteConfigRepository) : ViewModel() {
    private val _remoteConfigurationState = MutableStateFlow<RemoteConfigurationState>(RemoteConfigurationState.Loading)
    val remoteConfigurationState: StateFlow<RemoteConfigurationState> = _remoteConfigurationState

    fun processIntent(intent: RemoteConfigIntent) {
        if (intent == RemoteConfigIntent.Fetch) fetchRemoteConfiguration()
    }

    private fun fetchRemoteConfiguration() {
        viewModelScope.launch {
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
}