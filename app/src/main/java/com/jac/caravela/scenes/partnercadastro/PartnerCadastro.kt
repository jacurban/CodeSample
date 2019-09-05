package com.jac.caravela.scenes.partnercadastro

import com.jac.caravela.scenes.Scene

interface PartnerCadastro {
    interface View : Scene.View {
        fun successfulRegister()
        fun unsuccessfulRegister(msgRef: Int?)
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun postRegisterPartner(name: String, cnpj: String, email: String, description: String?)
        abstract fun postEditPartner(id: Int, name: String, cnpj: String, email: String, description: String?)
    }
}