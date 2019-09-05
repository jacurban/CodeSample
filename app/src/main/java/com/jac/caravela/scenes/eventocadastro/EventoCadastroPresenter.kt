package com.jac.caravela.scenes.eventocadastro

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventoCadastroPresenter(val view: EventoCadastro.View) : EventoCadastro.Presenter() {

    override fun postRegisterEvent(name: String, date: String, reference: String, latitude: String, longitude: String, description: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.eventRepository.postRegisterEvent(name, date, reference, latitude, longitude, description)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun postEditEvent(id: Int, name: String, date: String, reference: String, latitude: String, longitude: String, description: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.eventRepository.postEditEvent(id, name, date, reference, latitude, longitude, description)
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

}