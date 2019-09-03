package com.jac.caravela.scenes.subscribestudent

import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene


interface SubscribeStudent {
    interface View: Scene.View {
        fun successfulGetAll(students: List<User>)
        fun unsuccessfulGetAll(msgRef: Int?)
        fun successfulRegister()

    }
    abstract class Presenter: Scene.Presenter(){
        abstract fun getStudentByResponsible(responsibleId: Int?)
        abstract fun postRegisterEnrollment(studentId: Int, classId: Int?)
    }
}