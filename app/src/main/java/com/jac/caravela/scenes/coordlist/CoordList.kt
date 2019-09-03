package com.jac.caravela.scenes.coordlist

import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene


interface CoordList {
    interface View : Scene.View {
        fun successfulGetAll(coordList: List<User>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }

    abstract class Presenter : Scene.Presenter() {
        abstract fun getAllCoordinators()
        abstract fun deleteUser(id: Int)
    }
}