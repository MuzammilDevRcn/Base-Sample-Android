package com.muzammil.android.templates.koin

import com.muzammil.android.templates.core.manager.SharedPreferenceUtils
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class DiComponent : KoinComponent {
    val sharedPreferenceUtils by inject<SharedPreferenceUtils>()
}