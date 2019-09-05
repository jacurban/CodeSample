package com.jac.caravela.model

import android.os.Parcelable
import com.jac.caravela.service.responsebody.UserResponse
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Enrollment(
    val id: Int,
    val studentId: Int?,
    val classId: Int?,
    val enrollmentDate: String?,
    val statusId: Int?,
    val student: UserResponse,
    val responsible: UserResponse,
    val courseName: String?,
    val className: String?,
    val payments: List<Payment>?
) : Parcelable


