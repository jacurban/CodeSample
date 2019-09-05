package com.jac.caravela.scenes.turmacadastro

import com.jac.caravela.model.Lesson
import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene
import java.util.*


interface TurmaCadastro {
    interface View : Scene.View {
        fun successfulGetAll(teacherList: List<User>)
        fun successfulRegister()
        fun successfulEdit()
        fun unsuccessfulRegister(msgRef: Int?)
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun getAllTeachers()
        abstract fun postRegisterClass(
            name: String,
            localname: String,
            courseId: Int,
            teacherId: Int,
            latitude: String,
            longitude: String,
            lessons: List<Lesson>,
            price: String,
            initialDate: String?
        )

        abstract fun postEditClass(
            id: Int,
            name: String,
            localname: String,
            courseId: Int,
            teacherId: Int,
            latitude: String,
            longitude: String,
            lessons: List<Lesson>,
            price: String,
            initialDate: String?
        )
    }
}