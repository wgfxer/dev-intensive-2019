package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    var r = Rect()
    val view = this.currentFocus
    view?.getWindowVisibleDisplayFrame(r)

    val screenHeight: Int = view?.rootView?.height ?: 0

    val keypadHeight: Int = screenHeight - r.bottom

    return keypadHeight > screenHeight * 0.15
}


fun Activity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen()
}
