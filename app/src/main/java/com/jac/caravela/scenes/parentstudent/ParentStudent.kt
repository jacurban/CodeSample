package com.jac.caravela.scenes.parentstudent

import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene


interface ParentStudent {
    interface View: Scene.View {
        fun successfulGetStudentByResponsible(students: List<User>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter(){
        abstract fun getStudentByResponsible(responsibleId: Int?)
        abstract fun deleteUser(id: Int, responsibleId: Int?)
    }
}