package com.jac.caravela.scenes.cursocadastro

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CursoCadastroPresenter(val view: CursoCadastro.View) : CursoCadastro.Presenter() {
    override fun postRegiterCourse(name: String, description: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.courseRepository.postRegiterCourse(name, description)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun postEditCourse(id: Int, name: String, description: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.courseRepository.postEditCourse(id, name, description)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }
}