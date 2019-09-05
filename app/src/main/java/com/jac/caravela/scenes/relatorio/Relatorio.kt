package com.jac.caravela.scenes.relatorio

import com.jac.caravela.model.Report
import com.jac.caravela.scenes.Scene
import java.util.*


interface Relatorio {
    interface View : Scene.View {
        fun successfulGetReport(reportList: List<Report>)
        fun unsuccessfulGetReport (msgRef: Int?)
    }
    abstract class Presenter : Scene.Presenter() {
        abstract fun getReportStudents(initialDate: String, finalDate: String)
        abstract fun getReportResponsibles(initialDate: String, finalDate: String)
        abstract fun getReportClasses(initialDate: String, finalDate: String)
        abstract fun getReportEnrollments(initialDate: String, finalDate: String)
        abstract fun getReportPosts(initialDate: String, finalDate: String)
        abstract fun getReportMessages(initialDate: String, finalDate: String)
    }
}