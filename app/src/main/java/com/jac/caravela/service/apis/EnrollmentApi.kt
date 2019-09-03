package com.jac.caravela.service.apis

import com.jac.caravela.model.Enrollment
import com.jac.caravela.model.Report
import com.jac.caravela.service.requestbody.EnrollmentResquestBody
import com.jac.caravela.service.requestbody.RegisterEnrollmentResquestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface EnrollmentApi {

    @Headers("Content-Type: application/json")
    @POST("registerEnrollment/")
    fun postRegisterEnrollment(
        @Header("Authorization") token: String,
        @Body requestBody: RegisterEnrollmentResquestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("rejectEnrollment/")
    fun postRejectEnrollment(
        @Header("Authorization") token: String,
        @Body requestBody: EnrollmentResquestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("acceptEnrollment/")
    fun postAcceptEnrollment(
        @Header("Authorization") token: String,
        @Body requestBody: EnrollmentResquestBody
    ): Call<Void>

    @GET("getAllEnrollments/")
    fun getAllEnrollments(
        @Header("Authorization") token: String
    ): Call<List<Enrollment>>

    @GET("getReportEnrollments/")
    fun getReportEnrollments(
        @Header("Authorization") token: String,
        @Query("initialDate") initialDate: String,
        @Query("finalDate") finalDate: String
    ): Call<List<Report>>

    @Headers("Content-Type: application/json")
    @POST("deleteEnrollment/")
    fun deleteEnrollment(
        @Header("Authorization") token: String,
        @Body requestBody: EnrollmentResquestBody
    ): Call<Void>
}