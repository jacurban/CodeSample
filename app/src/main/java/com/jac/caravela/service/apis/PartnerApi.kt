package com.jac.caravela.service.apis

import com.jac.caravela.model.Partner
import com.jac.caravela.service.requestbody.EditPartnerRequestBody
import com.jac.caravela.service.requestbody.RegisterPartnerRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface PartnerApi{

    @Headers("Content-Type: application/json")
    @POST("registerPartner/")
    fun postRegisterPartner(
        @Header("Authorization") token: String,
        @Body requestBody: RegisterPartnerRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("editPartner/")
    fun postEditPartner(
        @Header("Authorization") token: String,
        @Body requestBody: EditPartnerRequestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("deletePartner/")
    fun postDeletePartner(
        @Header("Authorization") token: String,
        @Query("partnerId") partnerId: Int
    ): Call<Void>

    @GET("getAllPartner/")
    fun getAllPartner(
        @Header("Authorization") token: String
    ): Call<List<Partner>>

}