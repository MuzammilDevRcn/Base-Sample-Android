package com.muzammil.android.templates.revenue.userConsent.domain.repository

import android.app.Activity
import com.muzammil.android.templates.revenue.userConsent.domain.model.UserConsentState
import kotlinx.coroutines.flow.Flow

interface UserConsentRepository {
    val userConsentState: Flow<UserConsentState>
    val isPrivacySettingRequired: Flow<Boolean>

    fun startUserConsentRequestUiFlowIfNeeded(activity: Activity)
}