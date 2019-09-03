package com.jac.caravela.scenes.turmainfo

import com.jac.caravela.model.Classe
import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene

interface TurmaInfo {
    interface View : Scene.View {
        fun successfulGetAll(students: List<User>)
        fun successfulGetClass(classe: Classe?)
        fun unsuccessfulRequest(msgRef: Int?)
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun getStudentsByClass(classId: Int)
        abstract fun getClass(id: Int?)
    }
}