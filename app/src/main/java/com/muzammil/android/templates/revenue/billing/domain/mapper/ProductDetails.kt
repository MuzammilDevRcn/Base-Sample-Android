package com.muzammil.android.templates.revenue.billing.domain.mapper

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.ProductDetails

fun ProductDetails.getFormattedPrice(): String {
    return when (productType) {
        BillingClient.ProductType.SUBS -> {
            subscriptionOfferDetails
                ?.firstOrNull()
                ?.pricingPhases
                ?.pricingPhaseList
                ?.firstOrNull()
                ?.formattedPrice
                ?: "N/A"
        }

        BillingClient.ProductType.INAPP -> {
            oneTimePurchaseOfferDetails?.formattedPrice ?: "N/A"
        }

        else -> "N/A"
    }
}