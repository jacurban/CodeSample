package com.jac.caravela.scenes.payment

import com.jac.caravela.model.Payment
import com.jac.caravela.scenes.Scene

interface PaymentList {
    interface View : Scene.View {

        fun successfulPayByResponsible(payments: List<Payment>)
        fun unsuccessfulGet(msgRef: Int?)
    }
    abstract class Presenter : Scene.Presenter() {
        abstract fun getPaymentsByResponsible(responsibleId: Int?)
    }
}