package com.jac.caravela.scenes.personalinfo

import com.jac.caravela.core.App
import com.jac.caravela.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonalInfoPresenter (val view: PersonalInfo.View): PersonalInfo.Presenter() {
    override fun logout() {
        launch {
            view.getContext()?.let { App.restartApp(it) }
        }
    }

    override fun  updateLoggedUser(user: User) {
        job = launch {
            withContext(Dispatchers.IO){App.userRepository.updateLoggedUser(user)}
            view.successfulGetUser()
        }
    }

    override fun getAllPartner() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.partnerRepository.getAllPartner() }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun getUser(id: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO){ App.userRepository.getUser(id)}

            if (response.first)
                response.third?.let { updateLoggedUser(it)}
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}