package com.jac.caravela.scenes.matriculainfo

import com.jac.caravela.scenes.Scene


interface MatriculaInfo {
    interface View: Scene.View {
        fun successfulEnrollment()
        fun unsuccessfulFinishEnrollment(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun postRejectEnrollment(enrollmentId:Int?)
        abstract fun postAcceptEnrollment(enrollmentId:Int?)
        abstract fun deleteEnrollment(enrollmentId:Int?)

    }
}