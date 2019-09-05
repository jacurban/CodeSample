package com.jac.caravela.scenes.studentlist

import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene


interface StudentList {
    interface View: Scene.View {
        fun successfulGetAll(studentList: List<User>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getAllStudents()
        abstract fun deleteUser(id: Int)
    }
}