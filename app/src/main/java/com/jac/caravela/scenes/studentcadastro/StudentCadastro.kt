package com.jac.caravela.scenes.studentcadastro

import com.jac.caravela.scenes.Scene

interface StudentCadastro {
    interface View : Scene.View {
        fun successfulRegister()
        fun unsuccessfulRegister(msgRef: Int?)
        fun successfulEdit()
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun postRegisterStudent(
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String
        )

        abstract fun postEditStudent(
            id: Int?,
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String
        )
    }
}