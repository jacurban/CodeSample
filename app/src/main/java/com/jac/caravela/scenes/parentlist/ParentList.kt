package com.jac.caravela.scenes.parentlist

import com.jac.caravela.model.User
import com.jac.caravela.scenes.Scene


interface ParentList {
    interface View: Scene.View {
        fun successfulGetAll(parentList: List<User>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getAllResponsibles()
        abstract fun deleteUser(id: Int)
    }
}