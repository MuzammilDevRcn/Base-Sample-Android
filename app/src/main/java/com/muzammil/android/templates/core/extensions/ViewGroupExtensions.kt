package com.muzammil.android.templates.core.extensions

import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.muzammil.android.templates.core.utils.Constants.TAG


fun ViewGroup.addCleanView(view: View?) {
    if (view == null) {
        Log.e(TAG, "addCleanView: View ref is null")
        return
    }
    (view.parent as? ViewGroup)?.removeView(view)
    this.removeAllViews()
    view.let { this.addView(it) }
}