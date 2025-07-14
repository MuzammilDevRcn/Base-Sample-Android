package com.muzammil.android.templates.revenue.userConsent.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muzammil.android.templates.revenue.userConsent.domain.model.UserConsentState
import com.muzammil.android.templates.revenue.userConsent.domain.repository.UserConsentRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class UserConsentViewModel(private val userConsentRepository: UserConsentRepository) : ViewModel() {

    val userConsentState: StateFlow<UserConsentState> = userConsentRepository.userConsentState
        .stateIn(viewModelScope, SharingStarted.Eagerly, UserConsentState.UNKNOWN)

    fun requestUserConsentIfNeeded(activity: Activity) {
        userConsentRepository.startUserConsentRequestUiFlowIfNeeded(activity)
    }

}