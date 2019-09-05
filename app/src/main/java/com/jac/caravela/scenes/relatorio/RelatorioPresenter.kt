package com.jac.caravela.scenes.relatorio

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RelatorioPresenter(val view: Relatorio.View) : Relatorio.Presenter() {
    override fun getReportStudents(initialDate: String, finalDate: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.getReportStudents(initialDate, finalDate) }

            if (response.first)
                response.third?.let { view.successfulGetReport(it) }
            else
                view.unsuccessfulGetReport(response.second)
        }
    }

    override fun getReportResponsibles(initialDate: String, finalDate: String) {
        job = launch {
            val response =
                withContext(Dispatchers.IO) { App.userRepository.getReportResponsibles(initialDate, finalDate) }

            if (response.first)
                response.third?.let { view.successfulGetReport(it) }
            else
                view.unsuccessfulGetReport(response.second)
        }
    }

    override fun getReportClasses(initialDate: String, finalDate: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.classRepository.getReportClasses(initialDate, finalDate) }

            if (response.first)
                response.third?.let { view.successfulGetReport(it) }
            else
                view.unsuccessfulGetReport(response.second)
        }
    }

    override fun getReportEnrollments(initialDate: String, finalDate: String) {
        job = launch {
            val response =
                withContext(Dispatchers.IO) { App.enrollmentRepository.getReportEnrollments(initialDate, finalDate) }

            if (response.first)
                response.third?.let { view.successfulGetReport(it) }
            else
                view.unsuccessfulGetReport(response.second)
        }
    }

    override fun getReportPosts(initialDate: String, finalDate: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.postRepository.getReportPosts(initialDate, finalDate) }

            if (response.first)
                response.third?.let { view.successfulGetReport(it) }
            else
                view.unsuccessfulGetReport(response.second)
        }
    }

    override fun getReportMessages(initialDate: String, finalDate: String) {
        job = launch {
            val response =
                withContext(Dispatchers.IO) { App.messageRepository.getReportMessages(initialDate, finalDate) }

            if (response.first)
                response.third?.let { view.successfulGetReport(it) }
            else
                view.unsuccessfulGetReport(response.second)
        }
    }


}
