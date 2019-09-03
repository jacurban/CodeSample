package com.jac.caravela.scenes.chatnew

import com.jac.caravela.core.App
import com.jac.caravela.model.Chat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewChatPresenter(val view: NewChat.View) : NewChat.Presenter() {
    override fun getAllUsers() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.getAllUsers() }
            if (response.first)
                response.third?.let { users ->
                    val userId = App.user?.id ?: -1
                    val userName = App.user?.name ?: ""
                    val chats = mutableListOf<Chat>()
                    chats.addAll(
                        users
                            .filter { it.isAdministrator() && it.id != App.user?.id }
                            .map {
                                Chat(
                                    userOneId = userId,
                                    userOneName = userName,
                                    userOneType = App.user?.type,
                                    userTwoId = it.id,
                                    userTwoName = it.name,
                                    userTwoType = it.type
                                )
                            }
                    )
                    chats.addAll(
                        users
                            .filter { it.isCoordinator() && it.id != App.user?.id }
                            .map {
                                Chat(
                                    userOneId = userId,
                                    userOneName = userName,
                                    userOneType = App.user?.type,
                                    userTwoId = it.id,
                                    userTwoName = it.name,
                                    userTwoType = it.type
                                )
                            }
                    )
                    chats.addAll(
                        users
                            .filter { it.isTeacher() && it.id != App.user?.id }
                            .map {
                                Chat(
                                    userOneId = userId,
                                    userOneName = userName,
                                    userOneType = App.user?.type,
                                    userTwoId = it.id,
                                    userTwoName = it.name,
                                    userTwoType = it.type
                                )
                            }
                    )
                    chats.addAll(
                        users
                            .filter { it.isResponsible() && it.id != App.user?.id }
                            .map {
                                Chat(
                                    userOneId = userId,
                                    userOneName = userName,
                                    userOneType = App.user?.type,
                                    userTwoId = it.id,
                                    userTwoName = it.name,
                                    userTwoType = it.type
                                )
                            }
                    )
                    view.successfulGetAll(chats)
                }
            else
                view.unsuccessfulGetAll(response.second)
        }
    }
}