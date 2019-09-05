package com.jac.caravela.scenes.cursolist

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CursoListaPresenter(val view: CursoLista.View) : CursoLista.Presenter() {

    override fun getAllCourses() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.courseRepository.getAllCourses() }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun deleteCourse(id: Int) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.courseRepository.postDeleteCourse(id) }
            if (response.first)
                getAllCourses()
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}