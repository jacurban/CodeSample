package com.jac.caravela.service.requestbody

data class SendMessageRequestBody(
    val chatId: Int,
    val senderId: Int,
    val receiverId: Int,
    val description: String
)