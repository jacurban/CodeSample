package com.jac.caravela.scenes.personalinfo

import com.jac.caravela.model.Partner
import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene

interface PersonalInfo {
    interface View : Scene.View {
        fun successfulGetAll(partnerList: List<Partner>)
        fun unsuccessfulGetAll(msgRef: Int?)
        fun successfulGetUser()
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun logout()
        abstract fun getAllPartner()
        abstract fun getUser(id: Int?)
        abstract fun updateLoggedUser(user: User)
    }
}