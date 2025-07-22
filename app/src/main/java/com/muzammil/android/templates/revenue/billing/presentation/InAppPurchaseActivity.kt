package com.muzammil.android.templates.revenue.billing.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.billingclient.api.Purchase
import com.muzammil.android.templates.MainActivity
import com.muzammil.android.templates.R
import com.muzammil.android.templates.core.base.activities.AbsBaseActivity
import com.muzammil.android.templates.core.extensions.collectWhenCreated
import com.muzammil.android.templates.core.extensions.getResString
import com.muzammil.android.templates.core.extensions.openWebUrl
import com.muzammil.android.templates.core.extensions.showToast
import com.muzammil.android.templates.databinding.ActivityInAppPurchaseBinding
import com.muzammil.android.templates.revenue.billing.data.dataSource.inAppPurchaseListItem
import com.muzammil.android.templates.revenue.billing.presentation.mvi.BillingUiState
import com.muzammil.android.templates.revenue.billing.presentation.mvi.BillingViewModel
import com.muzammil.android.templates.revenue.billing.presentation.mvi.ProductType
import org.koin.androidx.viewmodel.ext.android.viewModel

class InAppPurchaseActivity : AbsBaseActivity<ActivityInAppPurchaseBinding>(ActivityInAppPurchaseBinding::inflate) {

    private val viewModel: BillingViewModel by viewModel()

    private lateinit var adapter: AdapterInAppPurchase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.queryProductDetails()
        viewModel.queryOwnedPurchases()

        setupRecyclerview()
        setupClickListeners()

        observeStateChanges()
    }

    private fun observeStateChanges() {
        viewModel.billingState.collectWhenCreated(this@InAppPurchaseActivity) { state ->
            when (state) {
                is BillingUiState.Idle -> {
                    showLoading(true)
                }

                is BillingUiState.Connected -> {
                    Log.i(TAG, "Billing connected")
                    showLoading(false)
                }

                is BillingUiState.Disconnected -> {
                    Log.w(TAG, "Billing disconnected")
                    showLoading(false)
                }

                is BillingUiState.ProductDetails -> {
                    showLoading(false)
                }

                is BillingUiState.Purchases -> {
                    showLoading(false)
                    updatePurchasesUI(state.purchases)
                }

                is BillingUiState.Error -> {
                    showLoading(false)
                    Log.e(TAG, "observeViewModel: ${state.message ?: getString(R.string.error_occurred)}")
                }
            }
        }
    }

    private fun updatePurchasesUI(purchases: List<Purchase>) {
        val hasPremium = purchases.any {
            it.products.contains(getResString(R.string.remove_ads_product_id_lifetime)) || it.products.contains(getResString(R.string.remove_ads_product_id_yearly)) || it.products.contains(
                getResString(R.string.remove_ads_product_id_monthly)
            )
        }
        diComponent.sharedPreferenceUtils.isAppPurchased = hasPremium
        Log.i(TAG, "User premium status updated: $hasPremium")
    }

    private fun showLoading(show: Boolean) {
        Log.i(TAG, "showLoading: if available: $show")
    }

    private fun setupRecyclerview() {
        adapter = AdapterInAppPurchase { updatedList, selectedPlan ->
            adapter.submitList(updatedList)
            viewModel.selectProductType(selectedPlan)
        }

        binding.recyclerView.apply {
            adapter = this@InAppPurchaseActivity.adapter
            layoutManager = LinearLayoutManager(this@InAppPurchaseActivity, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
        }
        adapter.submitList(inAppPurchaseListItem)
    }

    private fun setupClickListeners() {
        binding.apply {
            closePremium.setOnClickListener { navigateToMainScreen() }
            purchase.setOnClickListener {
                when (viewModel.selectedProductType.value) {
                    ProductType.WEEKLY -> launchPurchaseById(getResString(R.string.remove_ads_product_id_weekly))
                    ProductType.MONTHLY -> launchPurchaseById(getResString(R.string.remove_ads_product_id_monthly))
                    ProductType.YEARLY -> launchPurchaseById(getResString(R.string.remove_ads_product_id_yearly))
                    ProductType.LIFETIME -> launchPurchaseById(getResString(R.string.remove_ads_product_id_lifetime))
                    ProductType.NONE -> showToast(getString(R.string.select_a_plan))
                }
            }
            privacyPolicy.setOnClickListener { openWebUrl(R.string.privacy_policy_link) }
            termAndServices.setOnClickListener { openWebUrl(R.string.term_n_policy_link) }
        }
    }

    private fun navigateToMainScreen() {
        startActivity(Intent(this@InAppPurchaseActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        })
    }

    private fun launchPurchaseById(productId: String) {
        viewModel.getProductDetails(productId)?.let { viewModel.launchPurchase(this, it.productId) } ?: showToast(getString(R.string.product_not_available))
    }
}

private const val TAG = "InAppPurchaseActivityLogs"