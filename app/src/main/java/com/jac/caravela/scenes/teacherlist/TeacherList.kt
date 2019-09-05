package com.jac.caravela.scenes.teacherlist

import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene


interface TeacherList {
    interface View: Scene.View {
        fun successfulGetAll(teacherList: List<User>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getAllTeachers()
        abstract fun deleteUser(id: Int)
    }
}