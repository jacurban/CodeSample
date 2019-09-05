package com.jac.caravela.repositories

import android.util.Log
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Chat
import com.jac.caravela.service.apis.ChatApi
import com.jac.caravela.service.requestbody.RegisterChatRequestBody
import java.io.IOException

class ChatRepository(private val chatApi: ChatApi) {
    fun postRegisterChat(userOneId: Int, userTwoId: Int): Triple<Boolean, Int?, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = chatApi.postRegisterChat(
            token,
            RegisterChatRequestBody(userOneId, userTwoId)
        )
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.id)
            } else {
                Log.d("API-CALL", "postLogin Response Error: ${response.errorBody()?.toString()}")
                if (response.code() == 401)
                    return Triple(false, R.string.error_invalid_token, null)
            }
        } catch (e: IOException) {
            Log.e("API-CALL", e.message)
            return Triple(false, R.string.error_network, null)
        } catch (e: RuntimeException) {
            Log.e("API-CALL", e.message)
        }
        return Triple(false, R.string.error_unspecified, null)
    }

    fun getAllChatsByUser(): Triple<Boolean, Int?, List<Chat>?> {
        val token = App.user?.getBearerToken() ?: ""
        val userId = App.user?.id ?: -1
        val call = chatApi.getAllChatsByUser(token, userId)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "getAllResponsible successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body())
            } else {
                Log.d("API-CALL", "getAllResponsible Response Error: ${response.errorBody()?.toString()}")
                if (response.code() == 401)
                    return Triple(false, R.string.error_invalid_token, null)
            }
        } catch (e: IOException) {
            Log.e("API-CALL", e.message)
            return Triple(false, R.string.error_network, null)
        } catch (e: RuntimeException) {
            Log.e("API-CALL", e.message)
        }
        return Triple(false, R.string.error_unspecified, null)
    }
}