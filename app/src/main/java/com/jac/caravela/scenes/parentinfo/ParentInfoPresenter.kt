package com.jac.caravela.scenes.parentinfo

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ParentInfoPresenter(val view: ParentInfo.View) : ParentInfo.Presenter() {
    override fun getStudentByResponsible(responsibleId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.getStudentByResponsible(responsibleId) }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}