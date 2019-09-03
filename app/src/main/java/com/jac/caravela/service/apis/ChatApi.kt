package com.jac.caravela.service.apis

import com.jac.caravela.model.Chat
import com.jac.caravela.service.requestbody.RegisterChatRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface ChatApi {
    @Headers("Content-Type: application/json")
    @POST("registerChat/")
    fun postRegisterChat(
        @Header("Authorization") token: String,
        @Body requestBody: RegisterChatRequestBody
    ): Call<BaseResponse>

    @GET("getAllChatsByUser/")
    fun getAllChatsByUser(
        @Header("Authorization") token: String,
        @Query("userId") userId: Int
    ): Call<List<Chat>>
}