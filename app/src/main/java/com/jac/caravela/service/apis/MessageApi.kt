package com.jac.caravela.service.apis

import com.jac.caravela.model.Message
import com.jac.caravela.model.Report
import com.jac.caravela.service.requestbody.SendMessageRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface MessageApi {
    @Headers("Content-Type: application/json")
    @POST("sendMessage/")
    fun postSendMessage(
        @Header("Authorization") token: String,
        @Body requestBody: SendMessageRequestBody
    ): Call<Void>

    @GET("getAllMessagesByChat/")
    fun getAllMessagesByChat(
        @Header("Authorization") token: String,
        @Query("chatId") chatId: Int,
        @Query("userId") userId: Int
    ): Call<List<Message>>

    @GET("getReportMessages/")
    fun getReportMessages(
        @Header("Authorization") token: String,
        @Query("initialDate") initialDate: String,
        @Query("finalDate") finalDate: String
    ): Call<List<Report>>
}