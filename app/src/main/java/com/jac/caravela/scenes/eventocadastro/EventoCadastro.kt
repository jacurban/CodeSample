package com.jac.caravela.scenes.eventocadastro

import com.jac.caravela.scenes.Scene

interface EventoCadastro {
    interface View : Scene.View {
        fun successfulRegister()
        fun unsuccessfulRegister(msgRef: Int?)
    }
    abstract class Presenter : Scene.Presenter() {
        abstract fun postRegisterEvent(
            name: String,
            date: String,
            reference: String,
            latitude: String,
            longitude: String,
            description: String
        )
        abstract fun postEditEvent(
            id:Int,
            name: String,
            date: String,
            reference: String,
            latitude: String,
            longitude: String,
            description: String
        )
    }
}