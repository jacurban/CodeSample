package com.jac.caravela.extensions

import android.widget.EditText
import com.jac.caravela.R

fun EditText.checkIfIsBlank(): Boolean {
    val value = text?.toString() ?: ""
    val errorMsg = context?.getString(R.string.error_empty)

    return if (value.isBlank()) {
        error = errorMsg
        true
    } else
        false
}