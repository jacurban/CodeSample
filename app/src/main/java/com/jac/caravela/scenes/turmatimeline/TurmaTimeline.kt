package com.jac.caravela.scenes.turmatimeline

import com.jac.caravela.model.Message
import com.jac.caravela.model.Post
import com.jac.caravela.scenes.Scene


interface TurmaTimeline {
    interface View: Scene.View {
        fun successfulGetAll(postList: List<Post>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun postSendPost(userId: Int?, description: String, classId: Int?)
        abstract fun getAllPostsByClass(classId: Int?)
    }
}