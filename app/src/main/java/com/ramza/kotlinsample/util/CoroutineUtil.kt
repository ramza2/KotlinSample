package com.ramza.kotlinsample.util

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI


internal val Background = newFixedThreadPoolContext(2, "bg")

class CoroutineLifecycleListener(val deferred: Deferred<*>) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cancelCoroutine() {
        if (!deferred.isCancelled) {
            deferred.cancel()
        }
    }
}

fun <T> LifecycleOwner.load(loader: () -> T): Deferred<T> {
    val deferred = async(context = Background,
            start = CoroutineStart.LAZY) {
        loader()
    }

    lifecycle.addObserver(CoroutineLifecycleListener(deferred))
    return deferred
}

infix fun <T> Deferred<T>.then(block: (T) -> Unit): Job {
    return launch(context = UI) {
        block(this@then.await())
    }
}
