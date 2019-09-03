package com.jac.caravela.scenes.coordcadastro

import com.jac.caravela.scenes.Scene

interface CoordCadastro {
    interface View : Scene.View {
        fun successfulRegister()
        fun unsuccessfulRegister(msgRef: Int?)
        fun successfulEdit()
    }

    abstract class Presenter : Scene.Presenter() {

        abstract fun postRegisterCoordinator(
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String,
            password: String
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