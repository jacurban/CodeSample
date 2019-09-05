package com.jac.caravela.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Classe (
    val id: Int,
    val name: String,
    val localName: String,
    val courseId: Int,
    val courseName: String,
    val teacherId: Int,
    val teacherName: String?,
    val createDate: String,
    val latitude: String,
    val longitude: String,
    val lessons: List<Lesson>,
    val price: String?,
    val initialDate: Date?
) : Parcelable {
    fun getLagLng() = LatLng(latitude.toDouble(), longitude.toDouble())
}
