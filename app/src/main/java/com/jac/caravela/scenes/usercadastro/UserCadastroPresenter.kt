package com.jac.caravela.scenes.usercadastro

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserCadastroPresenter(val view: UserCadastro.View) : UserCadastro.Presenter() {
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

    override fun postRegisterAdministrator(
        name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postRegisterAdministrator(name, birth, cpf, phone, email, password)
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

    override fun postEditTeacher(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository
                    .postEditTeacher(id, name, birth, cpf, phone, email)
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
                view.successfulRegister()
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
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }
}