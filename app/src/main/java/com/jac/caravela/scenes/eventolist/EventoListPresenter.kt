package com.jac.caravela.scenes.eventolist

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventoListPresenter (val view: EventoList.View): EventoList.Presenter() {
    override fun getAllEvents() {
        job = launch {
            val response = withContext(Dispatchers.IO){ App.eventRepository.getAllEvents()}

            if (response.first)
                response.third?.let { view.successfulGetAll(it)}
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun deleteEvent(eventId: Int) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.eventRepository.postDeleteEvent(eventId) }
            if (response.first)
                getAllEvents()
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}