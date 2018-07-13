package com.ramza.kotlinsample.dialog

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog

/*
 * Notes Dialog
 */
inline fun Activity.showNotesAlertDialog(func: NotesDialogHelper.() -> Unit): AlertDialog =
        NotesDialogHelper(this).apply {
            func()
        }.create()

inline fun Fragment.showNotesAlertDialog(func: NotesDialogHelper.() -> Unit): AlertDialog =
        NotesDialogHelper(this.context!!).apply {
            func()
        }.create()
