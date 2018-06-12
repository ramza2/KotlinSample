package com.ramza.kotlinsample.util

import android.animation.Animator
import android.view.ViewPropertyAnimator

fun ViewPropertyAnimator.setListener(init: AnimListenerHelper.() -> Unit) {
    val listener = AnimListenerHelper()
    listener.init()
    this.setListener(listener)
}

private typealias AnimListener = (Animator) -> Unit

class AnimListenerHelper : Animator.AnimatorListener {

    private var animationStart: AnimListener? = null

    fun onAnimationStart(onAnimationStart: AnimListener) {
        animationStart = onAnimationStart
    }

    override fun onAnimationStart(animation: Animator) {
        animationStart?.invoke(animation)
    }

    private var animationRepeat: AnimListener? = null

    fun onAnimationRepeat(onAnimationRepeat: AnimListener) {
        animationRepeat = onAnimationRepeat
    }

    override fun onAnimationRepeat(animation: Animator) {
        animationRepeat?.invoke(animation)
    }

    private var animationCancel: AnimListener? = null

    fun onAnimationCancel(onAnimationCancel: AnimListener) {
        animationCancel = onAnimationCancel
    }

    override fun onAnimationCancel(animation: Animator) {
        animationCancel?.invoke(animation)
    }

    private var animationEnd: AnimListener? = null

    fun onAnimationEnd(onAnimationEnd: AnimListener) {
        animationEnd = onAnimationEnd
    }

    override fun onAnimationEnd(animation: Animator) {
        animationEnd?.invoke(animation)
    }
}