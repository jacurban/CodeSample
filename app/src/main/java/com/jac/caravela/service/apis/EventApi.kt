package com.jac.caravela.service.apis

import com.jac.caravela.model.Evento
import com.jac.caravela.service.requestbody.BaseRequestBody
import com.jac.caravela.service.requestbody.EventRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface EventApi{

    @Headers("Content-Type: application/json")
    @POST("registerEvent/")
    fun postRegisterEvent(
        @Header("Authorization") token: String,
        @Body requestBody: EventRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("editEvent/")
    fun postEditEvent(
        @Header("Authorization") token: String,
        @Body requestBody: EventRequestBody
    ): Call<Void>

    @GET("getAllEvent/")
    fun getAllEvents(
        @Header("Authorization") token: String
    ): Call<List<Evento>>

    @Headers("Content-Type: application/json")
    @POST("deleteEvent/")
    fun postDeleteEvent(
        @Header("Authorization") token: String,
        @Body requestBody: BaseRequestBody
    ): Call<Void>
}