package com.muzammil.android.templates.revenue

import com.muzammil.android.templates.revenue.userConsent.domain.model.AdState
import com.muzammil.android.templates.revenue.userConsent.domain.model.UserConsentState


data class AdsViewState(
    val adState: AdState = AdState.NOT_INITIALIZED,
    val isPrivacySettingRequired: Boolean = false,
    val userConsentState: UserConsentState = UserConsentState.CAN_REQUEST_ADS,
)