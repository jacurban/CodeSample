package com.jac.caravela.scenes.studentinfo

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentInfoPresenter (val view: StudentInfo.View): StudentInfo.Presenter() {
    override fun getClassByStudent(id: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO){ App.classRepository.getClassByStudent(id)}

            if (response.first)
                response.third?.let { view.successfulGetAll(it)}
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun getUser(id: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO){ App.userRepository.getUser(id)}

            if (response.first)
                response.third?.let { view.successfulGetUser(it)}
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}