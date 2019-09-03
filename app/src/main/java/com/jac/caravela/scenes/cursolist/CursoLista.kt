package com.jac.caravela.scenes.cursolist

import com.jac.caravela.model.Course
import com.jac.caravela.scenes.Scene


interface CursoLista {
    interface View: Scene.View {
        fun successfulGetAll(courseList: List<Course>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getAllCourses()
        abstract fun deleteCourse(id: Int)
    }
}