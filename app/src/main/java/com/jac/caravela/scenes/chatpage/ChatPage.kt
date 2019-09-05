package com.jac.caravela.scenes.chatpage

import com.jac.caravela.model.Chat
import com.jac.caravela.model.Message
import com.jac.caravela.scenes.Scene


interface ChatPage {
    interface View: Scene.View {
        fun successfulGetAll(messages: List<Message>?)
        fun unsuccessfulGetAll(msgRef: Int?)
    }
    abstract class Presenter: Scene.Presenter() {
        abstract fun getAllMessagesByChat(chatId: Int)
        abstract fun registerChat(senderId: Int, receiverId: Int, description: String)
        abstract fun sendMessage(chatId: Int?, senderId: Int, receiverId: Int, description: String)
    }
}