package com.jac.caravela.model

import android.os.Parcelable
import com.jac.caravela.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Chat (
    val id: Int? = null,
    val userOneId: Int,
    val userOneName: String?,
    val userOneStatusId: Int? = null,
    val userOneType: Int? = null,
    val userTwoId: Int,
    val userTwoName: String?,
    val userTwoStatusId: Int? = null,
    val userTwoType: Int? = null
): Parcelable {

    fun getUserOneTypeRef(): Int {
        return userOneType?.let { when {
            UserType.isAdministrator(it) -> R.string.administrador
            UserType.isResponsible(it) -> R.string.responsavel
            UserType.isTeacher(it) -> R.string.professor
            UserType.isCoordinator(it) -> R.string.coordenador
            UserType.isStudent(it) -> R.string.aluno
            else -> 0
        } } ?: 0
    }

    fun getUserTwoTypeRef(): Int {
        return userTwoType?.let { when {
            UserType.isAdministrator(it) -> R.string.administrador
            UserType.isResponsible(it) -> R.string.responsavel
            UserType.isTeacher(it) -> R.string.professor
            UserType.isCoordinator(it) -> R.string.coordenador
            UserType.isStudent(it) -> R.string.aluno
            else -> 0
        } } ?: 0
    }
}