package com.jac.caravela.scenes.login

import com.jac.caravela.scenes.Scene


interface Login {
    interface View: Scene.View {
        fun postLoginSuccessful()
        fun postLoginUnsuccessful(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter(){
        abstract fun postLogin(email: String, password: String)
    }
}