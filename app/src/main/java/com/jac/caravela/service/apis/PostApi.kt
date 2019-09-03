package com.jac.caravela.service.apis

import com.jac.caravela.model.Post
import com.jac.caravela.model.Report
import com.jac.caravela.service.requestbody.SendPostRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface PostApi {
    @Headers("Content-Type: application/json")
    @POST("sendPost/")
    fun postSendPost(
        @Header("Authorization") token: String,
        @Body requestBody: SendPostRequestBody
    ): Call<BaseResponse>

    @GET("getAllPostsByClass/")
    fun getAllPostsByClass(
        @Header("Authorization") token: String,
        @Query("classId") classId: Int?
    ): Call<List<Post>>

    @GET("getReportPosts/")
    fun getReportPosts(
        @Header("Authorization") token: String,
        @Query("initialDate") initialDate: String,
        @Query("finalDate") finalDate: String
    ): Call<List<Report>>


}