package com.jac.caravela.scenes.parentlist

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParentListPresenter(val view: ParentList.View) : ParentList.Presenter() {
    override fun getAllResponsibles() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.getAllResponsibles() }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun deleteUser(id: Int) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.postDeleteUser(id) }
            if (response.first)
                getAllResponsibles()
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}