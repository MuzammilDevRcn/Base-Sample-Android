package com.muzammil.android.templates.core.utils

import android.os.Handler
import android.os.Looper

fun withDelay(delay: Long = 300, callback: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(callback, delay)
}