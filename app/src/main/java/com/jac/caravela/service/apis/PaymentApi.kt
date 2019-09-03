package com.jac.caravela.service.apis

import com.jac.caravela.model.Payment
import com.jac.caravela.model.Report
import com.jac.caravela.service.requestbody.RegisterPaymentRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface PaymentApi{

    @Headers("Content-Type: application/json")
    @POST("registerPayment/")
    fun postRegisterPayment(
        @Header("Authorization") token: String,
        @Body requestBody: RegisterPaymentRequestBody
    ): Call<BaseResponse>

    @GET("getPaymentsByResponsible/")
    fun getPaymentsByResponsible(
        @Header("Authorization") token: String,
        @Query("responsibleId") responsibleId: Int?
    ): Call<List<Payment>>

    @GET("getPaymentsByEnrollment/")
    fun getPaymentsByEnrollment(
        @Header("Authorization") token: String,
        @Query("enrollmentId") enrollmentId: Int?
    ): Call<List<Payment>>
}