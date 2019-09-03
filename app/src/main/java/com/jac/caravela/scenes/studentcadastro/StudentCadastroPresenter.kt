package com.jac.caravela.scenes.studentcadastro

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StudentCadastroPresenter(val view: StudentCadastro.View) : StudentCadastro.Presenter() {
    override fun postRegisterStudent(
        name: String, birth: String, cpf: String, phone: String, email: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postRegisterStudent(name, birth, cpf, phone, email)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun postEditStudent(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postEditStudent(id, name, birth, cpf, phone, email)
            }
            if (response.first)
                view.successfulEdit()
            else
                view.unsuccessfulRegister(response.second)
        }
    }
}