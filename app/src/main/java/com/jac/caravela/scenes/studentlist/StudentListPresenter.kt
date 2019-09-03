package com.jac.caravela.scenes.studentlist

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentListPresenter (val view: StudentList.View): StudentList.Presenter() {
    override fun getAllStudents() {
        job = launch {
            val response = withContext(Dispatchers.IO){ App.userRepository.getAllStudents()}
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun deleteUser(id: Int) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.postDeleteUser(id) }
            if (response.first)
                getAllStudents()
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

}