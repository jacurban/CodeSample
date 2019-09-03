package com.jac.caravela.extensions

import android.app.Activity
import android.widget.Toast

fun Activity.showShortMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Activity.showLongMessage(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

