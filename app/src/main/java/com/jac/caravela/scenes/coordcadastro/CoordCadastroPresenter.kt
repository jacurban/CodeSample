package com.jac.caravela.scenes.coordcadastro

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoordCadastroPresenter(val view: CoordCadastro.View) : CoordCadastro.Presenter() {

    override fun postRegisterCoordinator(
        name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postRegisterCoordinator(name, birth, cpf, phone, email, password)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun postEditCoordinator(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postEditCoordinator(id, name, birth, cpf, phone, email)
            }
            if (response.first)
                view.successfulEdit()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun postEditAdministrator(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postEditAdministrator(id, name, birth, cpf, phone, email)
            }
            if (response.first)
                view.successfulEdit()
            else
                view.unsuccessfulRegister(response.second)
        }
    }
}
