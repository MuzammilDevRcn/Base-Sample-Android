package com.muzammil.android.templates.revenue.userConsent.data.configuration

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.muzammil.android.templates.revenue.userConsent.domain.model.RemoteAdState

/**
 * Use to simulate the ad state while in debug.
 *
 * The following command will change the ad state:
 * adb shell "am broadcast \
 *     -a com.muzammil.android.templates.revenue.userConsent.data.configuration.TEST_STATE \
 *     --es EXTRA_STATE RemoteAdState"
 *
 * With RemoteAdState being the class name of the wanted [RemoteAdState] or null.
 */
internal class DebugAdStateReceiver(
    private val onNewState: (RemoteAdState?) -> Unit,
) : SafeBroadcastReceiver(IntentFilter(BROADCAST_ACTION)) {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return
        intent.getStringExtra(EXTRA_STATE).toAdState().let(onNewState)
    }

    private fun String?.toAdState(): RemoteAdState? {
        val adState = when (this) {
            "SdkNotInitialized" -> RemoteAdState.SdkNotInitialized
            "Initialized" -> RemoteAdState.Initialized
            "Loading" -> RemoteAdState.Loading
            "NotShown" -> RemoteAdState.NotShown
            "Showing" -> RemoteAdState.Showing
            "Shown" -> RemoteAdState.Shown
            "Error" -> RemoteAdState.Error.NoImpressionError
            null -> null
            else -> {
                Log.e(TAG, "Invalid ad state")
                null
            }
        }

        Log.d(TAG, "Forcing $adState")

        return adState
    }
}

private const val BROADCAST_ACTION = "com.muzammil.android.templates.revenue.userConsent.data.configuration.TEST_STATE"
private const val EXTRA_STATE = "EXTRA_STATE"

private const val TAG = "DebugAdStateReceiver"