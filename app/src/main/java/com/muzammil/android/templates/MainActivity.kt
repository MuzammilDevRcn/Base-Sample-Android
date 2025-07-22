package com.muzammil.android.templates

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.muzammil.android.templates.core.base.activities.AbsBaseActivity
import com.muzammil.android.templates.core.extensions.delayDrawUntil
import com.muzammil.android.templates.databinding.ActivityMainBinding
import com.muzammil.android.templates.revenue.AdsViewModel
import com.muzammil.android.templates.revenue.remoteConfiguration.presentation.mvi.RemoteConfigIntent
import com.muzammil.android.templates.revenue.remoteConfiguration.presentation.mvi.RemoteConfigurationState
import com.muzammil.android.templates.revenue.userConsent.domain.model.UserConsentState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AbsBaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val adsViewModel by viewModel<AdsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialization()

        adsViewModel.requestUserConsentIfNeeded(this@MainActivity)
        observeUserConsent()
        observeRemoteConfig()
    }

    private fun initialization() {
        // Splash screen is dismissed on first frame drawn, delay it until we have a user consent status
        findViewById<View>(android.R.id.content).delayDrawUntil {
            adsViewModel.userConsentState.value != UserConsentState.UNKNOWN
        }

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun observeUserConsent() {
        lifecycleScope.launch {
            adsViewModel.userConsentState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest { userConsentState ->
                    when (userConsentState) {
                        UserConsentState.UNKNOWN -> Log.i(TAG, "User consent unknown")
                        UserConsentState.CANNOT_REQUEST_ADS -> Log.i(TAG, "User cannot request ads")
                        UserConsentState.CAN_REQUEST_ADS -> {
                            Log.i(TAG, "User can request ads")
                            adsViewModel.processIntent(RemoteConfigIntent.Fetch)
                        }

                        UserConsentState.ADS_NOT_NEEDED -> Log.i(TAG, "Ads not needed")
                    }
                }
        }
    }

    private fun observeRemoteConfig() {
        lifecycleScope.launch {
            adsViewModel.remoteConfigurationState.collectLatest { remoteConfigState ->
                Log.i(TAG, "onCreated: remoteFetched: $remoteConfigState ")
                when (remoteConfigState) {
                    is RemoteConfigurationState.Idle -> Log.i(TAG, "Idle")
                    is RemoteConfigurationState.Loading -> Log.i(TAG, "Fetching remote configuration")
                    is RemoteConfigurationState.Success -> Log.i(TAG, "Remote configuration fetched successfully: ${remoteConfigState.lastFetchTime}")
                    is RemoteConfigurationState.Error -> Log.i(TAG, "Error fetching remote configuration ${remoteConfigState.message}")
                }
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

private const val TAG = "MainActivityInfo"