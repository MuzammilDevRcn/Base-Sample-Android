package com.muzammil.android.templates.core.manager

enum class AppFlowState(val value: String) {
    FIRST("first"),
    SECOND("second"),
    THIRD("third");

    companion object {
        fun fromValue(value: String): AppFlowState = entries.find { it.value == value } ?: FIRST
    }
}
