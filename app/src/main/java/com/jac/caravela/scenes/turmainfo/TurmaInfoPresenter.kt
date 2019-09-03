package com.jac.caravela.scenes.turmainfo

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TurmaInfoPresenter (val view: TurmaInfo.View): TurmaInfo.Presenter() {

    override fun getStudentsByClass(classId: Int) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.getStudentsByClass(classId) }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulRequest(response.second)
        }
    }

    override fun getClass(id: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.classRepository.getClass(id) }
            if (response.first)
                response.third?.let { view.successfulGetClass(it) }
            else
                view.unsuccessfulRequest(response.second)
        }
    }
}