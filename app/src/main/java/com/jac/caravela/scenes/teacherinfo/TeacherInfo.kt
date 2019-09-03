package com.jac.caravela.scenes.teacherinfo

import com.jac.caravela.model.Classe
import com.jac.caravela.scenes.Scene


interface TeacherInfo {
    interface View: Scene.View {
        fun successfulGetAll(classList: List<Classe>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getClassesByTeacher(teacherId: Int?)
    }
}