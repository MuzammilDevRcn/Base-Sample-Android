package com.muzammil.android.templates.revenue.billing.data.dataSource

import com.muzammil.android.templates.R
import com.muzammil.android.templates.revenue.billing.domain.model.InAppPurchase
import com.muzammil.android.templates.revenue.billing.presentation.mvi.ProductType


val inAppPurchaseListItem = listOf(
    InAppPurchase(R.string.weekly, R.string.weekly_desc, price = "Rs 1500.0", selectedProduct = ProductType.WEEKLY),
    InAppPurchase(R.string.monthly, R.string.monthly_desc, price = "Rs 3200.0", selectedProduct = ProductType.MONTHLY),
    InAppPurchase(R.string.yearly, R.string.yearly_desc, price = "Rs 7000.0", selectedProduct = ProductType.YEARLY),
    InAppPurchase(R.string.lifetime, R.string.lifetime_desc, price = "Rs 2400.0", selectedProduct = ProductType.LIFETIME)
)