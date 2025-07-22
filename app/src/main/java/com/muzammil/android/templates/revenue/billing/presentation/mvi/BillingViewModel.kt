package com.muzammil.android.templates.revenue.billing.presentation.mvi

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.android.billingclient.api.Purchase
import com.muzammil.android.templates.revenue.billing.data.repository.BillingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BillingViewModel(private val repository: BillingRepository) : ViewModel() {

    val billingState: StateFlow<BillingUiState> = repository.billingState
    private val _selectedProductType = MutableStateFlow(ProductType.NONE)
    val selectedProductType: StateFlow<ProductType> = _selectedProductType.asStateFlow()

    fun selectProductType(type: ProductType) {
        _selectedProductType.value = type
    }

    fun launchPurchase(activity: Activity, productName: String) {
        repository.launchPurchase(activity, productName)
    }

    fun consumePurchase(purchase: Purchase) {
        repository.consumePurchase(purchase)
    }

    fun acknowledgePurchases(purchases: List<Purchase>) {
        repository.acknowledgePurchases(purchases)
    }

    fun queryProductDetails() = repository.queryProductDetails()
    fun queryOwnedPurchases() = repository.queryOwnedPurchases()
    fun isPurchased(productName: String) = repository.isPurchased(productName)
    fun getProductDetails(productName: String) = repository.getProductDetails(productName)
    fun endConnection() = repository.endConnection()
}

enum class ProductType {
    NONE, WEEKLY, MONTHLY, YEARLY, LIFETIME
}

