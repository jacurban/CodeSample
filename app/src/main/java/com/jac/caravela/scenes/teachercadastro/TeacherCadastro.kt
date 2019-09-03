package com.jac.caravela.scenes.teachercadastro

import com.jac.caravela.scenes.Scene

interface TeacherCadastro {
    interface View : Scene.View {
        fun successfulRegister()
        fun unsuccessfulRegister(msgRef: Int?)
        fun successfulEdit()
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun postRegisterTeacher(
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String,
            password: String
        )

        abstract fun postEditTeacher(
            id: Int?,
            name: String,
            birth: String,
            cpf: String,
            phone: String,
            email: String
        )
    }
}