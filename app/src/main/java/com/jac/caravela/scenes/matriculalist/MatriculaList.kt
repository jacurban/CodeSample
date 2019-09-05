package com.jac.caravela.scenes.matriculalist

import com.jac.caravela.model.Enrollment
import com.jac.caravela.scenes.Scene


interface MatriculaList {
    interface View: Scene.View {
        fun successfulGetAll(matriculaList: List<Enrollment>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getAllEnrollments()
    }
}