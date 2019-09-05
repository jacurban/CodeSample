package com.jac.caravela.scenes.turmalist

import com.jac.caravela.model.Classe
import com.jac.caravela.scenes.Scene

interface TurmaList {
    interface View: Scene.View {
        fun successfulGetClassByCourse(courseClasses: List<Classe>)
        fun unsuccessfulGetClassByCourse(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getClassesByCourse( courseId: Int?)
        abstract fun deleteClass(classId: Int, courseId: Int?)
    }
}