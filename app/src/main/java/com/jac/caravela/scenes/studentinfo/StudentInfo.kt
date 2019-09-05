package com.jac.caravela.scenes.studentinfo

import com.jac.caravela.model.Classe
import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene


interface StudentInfo {
    interface View : Scene.View {
        fun successfulGetAll(classList: List<Classe>)
        fun unsuccessfulGetAll(msgRef: Int?)
        fun successfulGetUser(user: User)
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun getClassByStudent(id: Int?)
        abstract fun getUser(id: Int?)
    }
}