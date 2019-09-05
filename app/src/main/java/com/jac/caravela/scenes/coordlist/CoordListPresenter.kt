package com.jac.caravela.scenes.coordlist

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CoordListPresenter(val view: CoordList.View) : CoordList.Presenter() {
    override fun getAllCoordinators() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.getAllCoordinators() }

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
                getAllCoordinators()
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}