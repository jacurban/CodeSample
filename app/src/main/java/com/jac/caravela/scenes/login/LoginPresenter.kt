package com.jac.caravela.scenes.login

import com.jac.caravela.core.App
import com.jac.caravela.model.User
import com.jac.caravela.persistence.DataBaseClient
import com.jac.caravela.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginPresenter (val view: Login.View): Login.Presenter() {
    override fun postLogin(email: String, password: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.userRepository.postLogin(email, password)
            }

            if (response.first)
                view.postLoginSuccessful()
            else
                view.postLoginUnsuccessful(response.second)
        }
    }
}