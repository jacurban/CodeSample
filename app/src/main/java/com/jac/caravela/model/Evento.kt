package com.jac.caravela.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Evento (
    var id: Int,
    var name: String,
    var local: String,
    var date: Date,
    var latitude: String,
    var longitude: String,
    var description: String?
) : Parcelable