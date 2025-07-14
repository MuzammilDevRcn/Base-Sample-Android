package com.muzammil.android.templates.revenue.remoteConfiguration.presentation.mvi

sealed interface RemoteConfigIntent {
    data object Fetch : RemoteConfigIntent
}