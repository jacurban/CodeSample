package com.jac.caravela.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Partner (
    val id: Int,
    val name: String,
    val cnpj: String,
    val email: String,
    val description: String
) : Parcelable