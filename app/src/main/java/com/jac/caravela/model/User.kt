package com.jac.caravela.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user")
@Parcelize
class User(
    @PrimaryKey
    var id: Int,
    var name: String,
    var cpf: String,
    var rg: String?,
    var birthDateString: String?,
    var email: String,
    var telephone: String?,
    var type: Int,
    var responsibleId: Int?,
    var password: String? = null
) : Parcelable {

    @IgnoredOnParcel
    var token: String? = null

    fun getBearerToken() = "Bearer $token"

    fun isAdministrator() = UserType.isAdministrator(type)
    fun isResponsible() = UserType.isResponsible(type)
    fun isTeacher() = UserType.isTeacher(type)
    fun isCoordinator() = UserType.isCoordinator(type)
    fun isStudent() = UserType.isStudent(type)
}