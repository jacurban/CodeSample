package com.jac.caravela.scenes.teacherlist

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeacherListPresenter (val view: TeacherList.View): TeacherList.Presenter() {
    override fun getAllTeachers() {
        job = launch {
            val response = withContext(Dispatchers.IO){ App.userRepository.getAllTeachers()}
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
                getAllTeachers()
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}