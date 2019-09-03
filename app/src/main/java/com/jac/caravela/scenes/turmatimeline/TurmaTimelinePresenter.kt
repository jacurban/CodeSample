package com.jac.caravela.scenes.turmatimeline

import com.jac.caravela.core.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TurmaTimelinePresenter(val view: TurmaTimeline.View) : TurmaTimeline.Presenter() {
    override fun postSendPost(userId: Int?, description: String, classId: Int?) {
        job = launch {
            val response =
                withContext(Dispatchers.IO) { App.postRepository.postSendPost(userId, description, classId) }
            if (response.first)
                getAllPostsByClass(classId)
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun getAllPostsByClass(classId: Int?) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.postRepository.getAllPostsByClass(classId) }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

}