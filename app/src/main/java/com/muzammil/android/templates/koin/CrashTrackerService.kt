package com.muzammil.android.templates.koin

import android.content.Context

interface CrashTrackerService {
    fun configure(context: Context)
    fun recordException(throwable: Throwable, message: String? = null)
    fun setUserId(userId: String)
    fun log(message: String)
    fun setCustomKey(key: String, value: String)
}