package com.muzammil.android.templates.revenue.billing.data.repository

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.Purchase
import com.muzammil.android.templates.revenue.billing.presentation.mvi.BillingUiState
import com.vojtkovszky.billinghelper.BillingEvent
import com.vojtkovszky.billinghelper.BillingHelper
import com.vojtkovszky.billinghelper.BillingListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BillingRepository(
    private val context: Context,
    private val inAppProducts: List<String>,
    private val subscriptions: List<String>
) : BillingListener {

    private val _billingState = MutableStateFlow<BillingUiState>(BillingUiState.Idle)
    val billingState: StateFlow<BillingUiState> = _billingState.asStateFlow()

    private val billingHelper = BillingHelper(
        context = context,
        productInAppPurchases = inAppProducts,
        productSubscriptions = subscriptions,
        billingListener = this
    )

    override fun onBillingEvent(event: BillingEvent, message: String?, responseCode: Int?, subResponseCode: Int?) {
        when (event) {
            BillingEvent.BILLING_CONNECTED -> _billingState.value = BillingUiState.Connected
            BillingEvent.BILLING_DISCONNECTED -> _billingState.value = BillingUiState.Disconnected
            BillingEvent.QUERY_PRODUCT_DETAILS_COMPLETE -> {
                val details = inAppProducts.mapNotNull { billingHelper.getProductDetails(it) } + subscriptions.mapNotNull { billingHelper.getProductDetails(it) }
                _billingState.value = BillingUiState.ProductDetails(details)
            }

            BillingEvent.QUERY_OWNED_PURCHASES_COMPLETE -> {
                val purchases = (inAppProducts + subscriptions).flatMap { billingHelper.getPurchasesWithProductName(it) }
                _billingState.value = BillingUiState.Purchases(purchases)
            }

            BillingEvent.PURCHASE_COMPLETE -> {
                /* handle as needed */
            }

            BillingEvent.PURCHASE_FAILED,
            BillingEvent.PURCHASE_CANCELLED,
            BillingEvent.PURCHASE_ACKNOWLEDGE_FAILED,
            BillingEvent.CONSUME_PURCHASE_FAILED,
            BillingEvent.QUERY_PRODUCT_DETAILS_FAILED,
            BillingEvent.QUERY_OWNED_PURCHASES_FAILED,
            BillingEvent.BILLING_CONNECTION_FAILED -> _billingState.value = BillingUiState.Error(message)

            BillingEvent.PURCHASE_ACKNOWLEDGE_SUCCESS -> {}
            BillingEvent.CONSUME_PURCHASE_SUCCESS -> {}
        }
    }

    fun launchPurchase(activity: Activity, productName: String) {
        println()
        billingHelper.launchPurchaseFlow(activity, productName)
    }

    fun consumePurchase(purchase: Purchase) {
        billingHelper.consumePurchase(purchase)
    }

    fun acknowledgePurchases(purchases: List<Purchase>) {
        billingHelper.acknowledgePurchases(purchases)
    }

    fun queryProductDetails() = billingHelper.initQueryProductDetails()
    fun queryOwnedPurchases() = billingHelper.initQueryOwnedPurchases()
    fun isPurchased(productName: String) = billingHelper.isPurchased(productName)
    fun getProductDetails(productName: String) = billingHelper.getProductDetails(productName)
    fun endConnection() = billingHelper.endClientConnection()
}