package com.jac.caravela.scenes.subscribestudent

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SubscribeStudentPresenter(val view: SubscribeStudent.View) : SubscribeStudent.Presenter() {
    override fun getStudentByResponsible(responsibleId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository.getStudentByResponsible(responsibleId)
            }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun postRegisterEnrollment(studentId: Int, classId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO){ App.enrollmentRepository.postRegisterEnrollment(studentId, classId)}
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}