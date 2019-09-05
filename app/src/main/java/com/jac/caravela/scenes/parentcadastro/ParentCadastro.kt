package com.jac.caravela.scenes.parentcadastro

import com.jac.caravela.scenes.Scene

interface ParentCadastro {
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

        abstract fun postEditResponsible(
            id: Int?,
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String,
            password: String
        )
    }
}