package com.jac.caravela.scenes.usercadastro

import com.jac.caravela.scenes.Scene

interface UserCadastro {
    interface View : Scene.View {
        fun successfulRegister()
        fun unsuccessfulRegister(msgRef: Int?)
        fun successfulEdit()
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun postRegisterResponsible(
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String,
            password: String
        )

        abstract fun postRegisterStudent(
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String
        )

        abstract fun postRegisterTeacher(
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String,
            password: String
        )

        abstract fun postRegisterCoordinator(
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String,
            password: String
        )

        abstract fun postRegisterAdministrator(
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String,
            password: String
        )

        abstract fun postEditResponsible(
            id: Int?,
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String,
            password: String
        )

        abstract fun postEditStudent(
            id: Int?,
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String
        )

        abstract fun postEditTeacher(
            id: Int?,
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String
        )

        abstract fun postEditCoordinator(
            id: Int?,
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String
        )

        abstract fun postEditAdministrator(
            id: Int?,
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String
        )
    }
}