package com.muzammil.android.templates.revenue.billing.di

import com.muzammil.android.templates.R
import com.muzammil.android.templates.core.extensions.getResString
import com.muzammil.android.templates.revenue.billing.data.repository.BillingRepository
import com.muzammil.android.templates.revenue.billing.presentation.mvi.BillingViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val billingModule = module {

    single {
        val inAppProducts = listOf(androidContext().getResString(R.string.remove_ads_product_id_lifetime))

        val subscriptions = listOf(
            androidContext().getResString(R.string.remove_ads_product_id_monthly),
            androidContext().getResString(R.string.remove_ads_product_id_yearly)
        )

        BillingRepository(get(), inAppProducts, subscriptions)
    }

    viewModel {
        BillingViewModel(get())
    }
}