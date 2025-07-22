package com.muzammil.android.templates.core.extensions

import android.app.Activity
import android.app.SearchManager
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import com.muzammil.android.templates.BuildConfig
import com.muzammil.android.templates.R

/* ---------------------------------------------- Resources ---------------------------------------------- */

fun Context?.getResString(@StringRes stringId: Int): String {
    return this?.resources?.getString(stringId) ?: ""
}

fun Context?.getDrawableResource(@DrawableRes drawableId: Int): Drawable? {
    return this?.let { ContextCompat.getDrawable(it, drawableId) }
}


/* ---------------------------------------------- Toast ---------------------------------------------- */

fun Context?.showToast(message: String) {
    (this as? Activity)?.runOnUiThread {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context?.showToast(@StringRes stringId: Int) {
    val message = getResString(stringId)
    showToast(message)
}

fun Context?.debugToast(message: String) {
    if (BuildConfig.DEBUG) {
        showToast(message)
    }
}

/* ---------------------------------------------- SnackBar ---------------------------------------------- */

fun Context?.showSnackBar(message: String) {
    (this as? Activity)?.runOnUiThread {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show()
    }
}

/* ---------------------------------------------- Intents ---------------------------------------------- */

fun Context?.openWebUrl(url: String) {
    this?.let {
        try {
            val uri = url.toUri()
            it.startActivity(Intent(Intent.ACTION_VIEW, uri))
        } catch (ex: Exception) {
            Log.e(TAG, "openPlayStoreApp: ", ex)
        }
    }
}

fun Context?.openWebUrl(@StringRes stringId: Int) {
    val url = getResString(stringId)
    openWebUrl(url)
}

fun Context?.openPlayStoreApp(packageName: String? = this?.packageName) {
    val playStoreUrl = "https://play.google.com/store/apps/details?id=$packageName"
    openWebUrl(playStoreUrl)
}

fun Context?.openEmailApp(emailAddress: String) {
    this?.let {
        val appName = getResString(R.string.app_name)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.type = "message/rfc822"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, appName)
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Feedback...")
        try {
            it.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Log.e(TAG, "openEmailApp: ", ex)
        }
    }
}

fun Context?.openEmailApp(@StringRes emailAddressId: Int) {
    val emailAddress = getResString(emailAddressId)
    openEmailApp(emailAddress)
}

fun Context?.shareApp() {
    this?.let {
        try {
            val playStoreUrl = "https://play.google.com/store/apps/details?id=$packageName"
            val appName = getResString(R.string.app_name)
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, appName)
            sendIntent.putExtra(Intent.EXTRA_TEXT, playStoreUrl)
            sendIntent.type = "text/plain"
            it.startActivity(sendIntent)
        } catch (ex: Exception) {
            Log.e(TAG, "shareApp: ", ex)
        }
    }
}

fun Context?.openSubscriptions() {
    this?.let {
        try {
            val playStoreUrl = "https://play.google.com/store/account/subscriptions?package=$packageName"
            startActivity(Intent(Intent.ACTION_VIEW, playStoreUrl.toUri()));
        } catch (ex: ActivityNotFoundException) {
            Log.e(TAG, "openSubscriptions: ", ex)
        }
    }
}

fun Context?.copyClipboardData(text: String) {
    this?.let {
        try {
            val clipboard = it.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("simple text", text)
            clipboard.setPrimaryClip(clip)
        } catch (ex: Exception) {
            Log.e(TAG, "copyClipboardData: ", ex)
        }
    }
}

fun Context?.shareTextData(data: String) {
    this?.let {
        try {
            val intentTextData = Intent(Intent.ACTION_SEND)
            intentTextData.type = "text/plain"
            intentTextData.putExtra(Intent.EXTRA_SUBJECT, "Data")
            intentTextData.putExtra(Intent.EXTRA_TEXT, data)
            it.startActivity(Intent.createChooser(intentTextData, "Choose to share"))
        } catch (ex: Exception) {
            Log.e(TAG, "shareTextData: ", ex)
        }
    }
}

fun Context?.searchData(text: String) {
    this?.let {
        try {
            val intentSearch = Intent(Intent.ACTION_WEB_SEARCH)
            intentSearch.putExtra(SearchManager.QUERY, text)
            it.startActivity(intentSearch)
        } catch (ex: Exception) {
            Log.e(TAG, "searchData: ", ex)
        }
    }
}

fun Context?.translateDate(data: String) {
    this?.let {
        try {
            val url = "https://translate.google.com/#view=home&op=translate&sl=auto&tl=en&text=$data"
            val intentTranslate = Intent(Intent.ACTION_VIEW)
            intentTranslate.data = url.toUri()
            it.startActivity(intentTranslate)
        } catch (ex: Exception) {
            Log.e(TAG, "translateDate: ", ex)
        }
    }
}