package com.ramza.kotlinsample

import android.animation.Animator
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewPropertyAnimator
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class SecondActivityUI : AnkoComponent<SecondActivity>{
    override fun createView(ui: AnkoContext<SecondActivity>)= ui.apply {
        linearLayout {
            verticalLayout{
                background = GradientDrawable().apply{
                    shape= GradientDrawable.RECTANGLE
                    //colors = intArrayOf( rgb(255,255,255), rgb(220,220,220), rgb(200,200,200),  rgb(180,180,180), rgb(150,150,150),rgb(255,255,255))
                    setStroke(2, Color.GRAY)
                }
                onClick {
                }
            }.lparams(width= matchParent)
        }
    }.view

}

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SecondActivityUI().setContentView(this)

        val name: String? = intent.getStringExtra("name")
        toast(name?:"test2")

        contentView!!.animate()
                .alpha(0f)
                .setListener {
                    onAnimationStart { toast("Start") }
                    onAnimationEnd { toast("End") }
                }
    }

}

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
