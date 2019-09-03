package com.jac.caravela.extensions

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showShortMessage(msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}