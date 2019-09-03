package com.jac.caravela.scenes.turmalist

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TurmaListPresenter(val view: TurmaList.View) : TurmaList.Presenter() {
    override fun deleteClass(classId: Int, courseId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.classRepository.postDeleteClass(classId) }
            if (response.first)
                getClassesByCourse(courseId)
            else
                view.unsuccessfulGetClassByCourse(response.second)
        }
    }

    override fun getClassesByCourse(courseId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.classRepository.getClassesByCourse(courseId) }
            if (response.first)
                response.third?.let { view.successfulGetClassByCourse(it) }
            else
                view.unsuccessfulGetClassByCourse(response.second)
        }
    }

}