package com.jac.caravela.scenes.teacherinfo

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeacherInfoPresenter (val view: TeacherInfo.View): TeacherInfo.Presenter() {
    override fun getClassesByTeacher(teacherId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.classRepository.getClassesByTeacher(teacherId) }

            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}