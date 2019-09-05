package com.jac.caravela.service.apis

import com.jac.caravela.model.Classe
import com.jac.caravela.model.Report
import com.jac.caravela.service.requestbody.ClassRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface ClassApi {
    @Headers("Content-Type: application/json")
    @POST("editClass/")
    fun postEditClass(
        @Header("Authorization") token: String,
        @Body requestBody: ClassRequestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("registerClass/")
    fun postRegisterClass(
        @Header("Authorization") token: String,
        @Body requestBody: ClassRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("deleteClass/")
    fun postDeleteClass(
        @Header("Authorization") token: String,
        @Body requestBody: ClassRequestBody
    ): Call<Void>

    @GET("getClass/")
    fun getClass(
        @Header("Authorization") token: String,
        @Query("id") id: Int?
    ): Call<Classe>

    @GET("getClassByStudent/")
    fun getClassByStudent(
        @Header("Authorization") token: String,
        @Query("studentId") studentId: Int?
    ): Call<List<Classe>>

    @GET("getClassesByCourse/")
    fun getClassesByCourse(
        @Header("Authorization") token: String,
        @Query("courseId") courseId: Int?
    ): Call<List<Classe>>

    @GET("getClassesByTeacher/")
    fun getClassesByTeacher(
        @Header("Authorization") token: String,
        @Query("teacherId") teacherId: Int?
    ): Call<List<Classe>>

    @GET("getAllClasses/")
    fun getAllClasses(
        @Header("Authorization") token: String
    ): Call<List<Classe>>

    @GET("getReportClasses/")
    fun getReportClasses(
        @Header("Authorization") token: String,
        @Query("initialDate") initialDate: String,
        @Query("finalDate") finalDate: String
    ): Call<List<Report>>
}