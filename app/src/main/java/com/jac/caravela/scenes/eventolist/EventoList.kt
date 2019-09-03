package com.jac.caravela.scenes.eventolist

import com.jac.caravela.model.Evento
import com.jac.caravela.scenes.Scene

interface EventoList {
    interface View : Scene.View {
        fun successfulGetAll(eventList: List<Evento>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun getAllEvents()
        abstract fun deleteEvent(eventId: Int)
    }
}