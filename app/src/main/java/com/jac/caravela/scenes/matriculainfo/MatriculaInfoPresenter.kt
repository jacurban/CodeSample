package com.jac.caravela.scenes.matriculainfo

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MatriculaInfoPresenter(val view: MatriculaInfo.View) : MatriculaInfo.Presenter() {
    override fun postRejectEnrollment(enrollmentId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.enrollmentRepository.postRejectEnrollment(enrollmentId)
            }
            if (response.first)
                view.successfulEnrollment()
            else
                view.unsuccessfulFinishEnrollment(response.second)
        }
    }

    override fun postAcceptEnrollment(enrollmentId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.enrollmentRepository.postAcceptEnrollment(enrollmentId)
            }
            if (response.first)
                view.successfulEnrollment()
            else
                view.unsuccessfulFinishEnrollment(response.second)
        }
    }

    override fun deleteEnrollment(enrollmentId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.enrollmentRepository.deleteEnrollment(enrollmentId)
            }
            if (response.first)
                view.successfulEnrollment()
            else
                view.unsuccessfulFinishEnrollment(response.second)
        }
    }
}