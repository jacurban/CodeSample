package com.jac.caravela.scenes.parentstudent

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParentStudentPresenter(val view: ParentStudent.View) : ParentStudent.Presenter() {
    override fun getStudentByResponsible(responsibleId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository.getStudentByResponsible(responsibleId)
            }
            if (response.first)
                response.third?.let { view.successfulGetStudentByResponsible(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun deleteUser(id: Int, responsibleId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.postDeleteUser(id) }
            if (response.first)
            getStudentByResponsible(responsibleId)
            else
            view.unsuccessfulGetAll(response.second)
        }
    }

}