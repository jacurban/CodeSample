package com.jac.caravela.service.responsebody

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jac.caravela.model.User
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.Date

@Parcelize
data class UserResponse(
    @Expose
    @SerializedName("id")
    var id: Int,
    @Expose
    @SerializedName("name")
    var name: String,
    @Expose
    @SerializedName("cpf")
    var cpf: String,
    @Expose
    @SerializedName("rg")
    var rg: String?,
    @Expose
    @SerializedName("birthDate")
    var birthDate: Date?,
    @Expose
    @SerializedName("email")
    var email: String,
    @Expose
    @SerializedName("telephone")
    var telephone: String?,
    @Expose
    @SerializedName("type")
    var type: Int,
    @Expose
    @SerializedName("responsibleId")
    var responsibleId: Int?,
    @Expose
    @SerializedName("password")
    var password: String? = null
) : Parcelable {

    val asUser
        get() = User(
            id = id,
            name = name,
            cpf = cpf,
            rg = rg,
            birthDateString = birthDate?.let { SimpleDateFormat("dd/MM/yyyy").format(it) },
            email = email,
            telephone = telephone,
            type = type,
            responsibleId = responsibleId,
            password = password
        )
}