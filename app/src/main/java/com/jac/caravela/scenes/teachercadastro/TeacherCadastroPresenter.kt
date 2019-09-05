package com.jac.caravela.scenes.teachercadastro

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TeacherCadastroPresenter(val view: TeacherCadastro.View) : TeacherCadastro.Presenter() {

    override fun postRegisterTeacher(
        name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postRegisterTeacher(name, birth, cpf, phone, email, password)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }


    override fun postEditTeacher(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postEditTeacher(id, name, birth, cpf, phone, email)
            }
            if (response.first)
                view.successfulEdit()
            else
                view.unsuccessfulRegister(response.second)
        }
    }
}