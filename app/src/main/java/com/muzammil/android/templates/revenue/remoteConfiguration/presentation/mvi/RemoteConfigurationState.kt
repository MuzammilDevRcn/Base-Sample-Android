package com.muzammil.android.templates.revenue.remoteConfiguration.presentation.mvi

sealed interface RemoteConfigurationState {


    data object Idle : RemoteConfigurationState
    data object Loading : RemoteConfigurationState
    data class Success(val lastFetchTime: Long) : RemoteConfigurationState
    data class Error(val message: String) : RemoteConfigurationState
}