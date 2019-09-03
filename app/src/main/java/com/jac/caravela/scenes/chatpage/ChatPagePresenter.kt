package com.jac.caravela.scenes.chatpage

import com.jac.caravela.core.App
import com.jac.caravela.model.Chat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatPagePresenter(val view: ChatPage.View) : ChatPage.Presenter() {
    override fun registerChat(senderId: Int, receiverId: Int, description: String) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.chatRepository.postRegisterChat(senderId, receiverId)
            }
            if (response.first)
                sendMessage(response.third, senderId, receiverId, description)
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

    override fun sendMessage(chatId: Int?, senderId: Int, receiverId: Int, description: String) {
        job = launch {
            chatId?.let { chatId ->
                val response = withContext(Dispatchers.IO) {
                    App.messageRepository.postSendMessage(chatId, senderId, receiverId, description)
                }
                if (response.first)
                    getAllMessagesByChat(chatId)
                else
                    view.unsuccessfulGetAll(response.second)
            } ?: registerChat(senderId, receiverId, description)
        }
    }

    override fun getAllMessagesByChat(chatId: Int) {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.messageRepository.getAllMessagesByChat(chatId) }
            if (response.first)
                view.successfulGetAll(response.third)
            else
                view.unsuccessfulGetAll(response.second)
        }
    }

}