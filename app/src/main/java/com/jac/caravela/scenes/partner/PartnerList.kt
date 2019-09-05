package com.jac.caravela.scenes.partner

import com.jac.caravela.model.Course
import com.jac.caravela.model.Partner
import com.jac.caravela.scenes.Scene

interface PartnerList {
    interface View: Scene.View {
        fun successfulGetAll(partnerList: List<Partner>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getAllPartner()
        abstract fun deletePartner(id: Int)
    }
}