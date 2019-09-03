package com.jac.caravela.scenes.chatlist

import com.jac.caravela.model.Chat
import com.jac.caravela.scenes.Scene

interface ChatList {
    interface View: Scene.View {
        fun successfulGetAll(chats: List<Chat>)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getAllChatsByUser()
    }
}