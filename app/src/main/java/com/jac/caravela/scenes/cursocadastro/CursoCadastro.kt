package com.jac.caravela.scenes.cursocadastro

import com.jac.caravela.scenes.Scene


interface CursoCadastro {
    interface View : Scene.View {
        fun successfulRegister()
        fun unsuccessfulRegister(msgRef: Int?)
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun postRegiterCourse(name: String, description: String)
        abstract fun postEditCourse(id: Int, name: String, description: String)
    }
}