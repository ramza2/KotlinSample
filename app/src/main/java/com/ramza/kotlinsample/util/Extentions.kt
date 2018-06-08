package com.ramza.kotlinsample.util

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat

fun <T> lazyFast(operation: () -> T): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
    operation()
}

fun Context.safeContext(): Context =
        takeUnless {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                isDeviceProtectedStorage
            } else {
                true
            }
        }?.run {
            applicationContext.let {
                ContextCompat.createDeviceProtectedStorageContext(it) ?: it
            }
        } ?: this