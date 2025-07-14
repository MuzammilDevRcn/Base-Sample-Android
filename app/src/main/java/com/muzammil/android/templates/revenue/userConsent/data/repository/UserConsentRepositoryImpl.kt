package com.muzammil.android.templates.revenue.userConsent.data.repository

import android.app.Activity
import com.muzammil.android.templates.revenue.userConsent.data.dataSource.UserConsentDataSource
import com.muzammil.android.templates.revenue.userConsent.domain.model.UserConsentState
import com.muzammil.android.templates.revenue.userConsent.domain.repository.UserConsentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine


class UserConsentRepositoryImpl(private val userConsentDataSource: UserConsentDataSource) : UserConsentRepository {

    override val userConsentState: Flow<UserConsentState> =
        combine(
            userConsentDataSource.isInitialized,
            userConsentDataSource.isUserConsentingForAds,
            ::toUserConsentState,
        )

    override val isPrivacySettingRequired: Flow<Boolean> = userConsentDataSource.isPrivacyOptionsRequired

    override fun startUserConsentRequestUiFlowIfNeeded(activity: Activity) {
        userConsentDataSource.requestUserConsent(activity)
    }

    private fun toUserConsentState(isConsentInit: Boolean, isConsenting: Boolean): UserConsentState =
        when {
            isConsenting -> UserConsentState.CAN_REQUEST_ADS
            isConsentInit && !isConsenting -> UserConsentState.CANNOT_REQUEST_ADS
            else -> UserConsentState.UNKNOWN
        }
}