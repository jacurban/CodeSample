package com.jac.caravela.scenes.chatlist

import com.jac.caravela.core.App
import com.jac.caravela.model.Chat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatListPresenter(val view: ChatList.View) : ChatList.Presenter() {
    override fun getAllChatsByUser() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.chatRepository.getAllChatsByUser() }
            if (response.first)
                response.third?.let { apiChats ->
                    val chats = mutableListOf<Chat>()
                    chats.addAll(
                        apiChats
                            .filter {
                                (App.user?.id == it.userOneId) && it.userOneStatusId == 1
                                        || (App.user?.id == it.userTwoId) && it.userTwoStatusId == 1
                            }
                    )
                    chats.addAll(
                        apiChats
                            .filter {
                                (App.user?.id == it.userOneId) && it.userOneStatusId == 2
                                        || (App.user?.id == it.userTwoId) && it.userTwoStatusId == 2
                            }
                    )
                    view.successfulGetAll(chats)
                }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}