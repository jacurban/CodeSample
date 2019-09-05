package com.jac.caravela.scenes.parentinfo

import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene


interface ParentInfo {
    interface View : Scene.View {
        fun successfulGetAll(studentList: List<User>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun getStudentByResponsible(responsibleId: Int?)
    }
}