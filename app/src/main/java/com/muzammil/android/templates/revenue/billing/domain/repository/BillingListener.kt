package com.muzammil.android.templates.revenue.billing.domain.repository

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingResult
import com.vojtkovszky.billinghelper.BillingEvent

interface BillingListener {
    /**
     * @param event one of resolved [BillingEvent]s
     * @param message as reported by [BillingResult.getDebugMessage]
     * @param responseCode as reported by [BillingResult.getResponseCode] as [BillingClient.BillingResponseCode]
     * @param subResponseCode as reported by [BillingResult.getOnPurchasesUpdatedSubResponseCode] as [BillingClient.OnPurchasesUpdatedSubResponseCode]
     */
    fun onBillingEvent(
        event: BillingEvent,
        message: String?,
        @BillingClient.BillingResponseCode responseCode: Int?,
        @BillingClient.OnPurchasesUpdatedSubResponseCode subResponseCode: Int?
    )
}