package com.jac.caravela.scenes.parentcadastro

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParentCadastroPresenter(val view: ParentCadastro.View) : ParentCadastro.Presenter() {
    override fun postRegisterResponsible(
        name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postRegisterResponsible(name, birth, cpf, phone, email, password)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun postEditResponsible(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postEditResponsible(id, name, birth, cpf, phone, email, password)
            }
            if (response.first)
                view.successfulEdit()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

}
