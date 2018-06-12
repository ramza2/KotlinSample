package com.ramza.kotlinsample

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ramza.kotlinsample.util.setListener
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
