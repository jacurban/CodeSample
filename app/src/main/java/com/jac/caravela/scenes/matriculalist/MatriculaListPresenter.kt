package com.jac.caravela.scenes.matriculalist

import com.jac.caravela.core.App
import com.jac.caravela.model.Enrollment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatriculaListPresenter(val view: MatriculaList.View) : MatriculaList.Presenter() {
    override fun getAllEnrollments() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.enrollmentRepository.getAllEnrollments() }

            if (response.first)
                response.third?.let {view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}