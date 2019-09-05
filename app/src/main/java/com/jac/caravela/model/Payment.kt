package com.jac.caravela.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Payment (
    val id: Int,
    val enrollmentId: Int,
    val statusId: Int,
    val createDate: String,
    val payDate: Date?,
    val cancelDate: Date?,
    val responsibleId: Int,
    val maturityDate: Date?,
    val boletoUrl: String,
    val transactionId: Int?,
    val price: Int?,
    val studentName: String?,
    val courseName: String?
): Parcelable
