package com.muzammil.android.templates.core.extensions

import android.content.Context
import androidx.annotation.StringRes

fun Context?.getResString(@StringRes stringId: Int): String {
    return this?.resources?.getString(stringId) ?: ""
}
