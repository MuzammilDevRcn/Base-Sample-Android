package com.muzammil.android.templates.revenue.billing.presentation.mvi

import com.android.billingclient.api.Purchase

sealed class BillingUiState {
    object Idle : BillingUiState()
    object Connected : BillingUiState()
    object Disconnected : BillingUiState()
    data class ProductDetails(val details: List<com.android.billingclient.api.ProductDetails>) : BillingUiState()
    data class Purchases(val purchases: List<Purchase>) : BillingUiState()
    data class Error(val message: String?) : BillingUiState()
}