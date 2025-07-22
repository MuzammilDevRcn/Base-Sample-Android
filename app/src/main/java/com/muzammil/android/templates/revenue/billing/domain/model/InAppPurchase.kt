package com.muzammil.android.templates.revenue.billing.domain.model

import androidx.annotation.StringRes
import com.muzammil.android.templates.revenue.billing.presentation.mvi.ProductType

data class InAppPurchase(
    @param:StringRes val title: Int,
    @param:StringRes val description: Int,
    val price: String = "",
    val selectedProduct: ProductType,
    val isSelected: Boolean = false
)