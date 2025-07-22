package com.muzammil.android.templates.revenue.billing.presentation.mvi

import com.android.billingclient.api.ProductDetails

sealed class BillingState {
    object Loading : BillingState()
    data class Error(val message: String) : BillingState()
    data class ProductsLoaded(
        val monthlyProduct: ProductDetails?,
        val yearlyProduct: ProductDetails?,
        val lifetimeProduct: ProductDetails?,
        val hasPremium: Boolean
    ) : BillingState()

    data class PremiumActivated(val hasPremium: Boolean) : BillingState()
    data class SelectionChanged(val selectedProduct: ProductType) : BillingState()
    data class PurchaseComplete(val purchasedProductId: String) : BillingState()
    object PurchaseCancelled : BillingState()
    data class PurchaseFailed(val message: String) : BillingState()
}
