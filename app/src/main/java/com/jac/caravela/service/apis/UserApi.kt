package com.jac.caravela.service.apis

import com.jac.caravela.model.Report
import com.jac.caravela.service.requestbody.LoginRequestBody
import com.jac.caravela.service.requestbody.UserRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import com.jac.caravela.service.responsebody.LoginResponse
import com.jac.caravela.service.responsebody.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface UserApi { // descricao da api de users

    @POST("confirmCode/")
    @FormUrlEncoded
    fun confirmCode(
        @Field("email") email: String,
        @Field("code") code: String
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("login/")
    fun postLogin(
        @Body requestBody: LoginRequestBody
    ): Call<LoginResponse>

    @Headers("Content-Type: application/json")
    @POST("registerResponsible/")
    fun postRegisterResponsible(
        @Body requestBody: UserRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("registerStudent/")
    fun postRegisterStudent(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("registerTeacher/")
    fun postRegisterTeacher(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("registerAdministrator/")
    fun postRegisterAdministrator(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("registerCoordinator/")
    fun postRegisterCoordinator(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("editResponsible/")
    fun postEditResponsible(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("editStudent/")
    fun postEditStudent(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("editTeacher/")
    fun postEditTeacher(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("editAdministrator/")
    fun postEditAdministrator(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("editCoordinator/")
    fun postEditCoordinator(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("deleteUser/")
    fun postDeleteUser(
        @Header("Authorization") token: String,
        @Body requestBody: UserRequestBody
    ): Call<Void>

    @GET("getStudentsByResponsible/")
    fun getStudentByResponsible(
        @Header("Authorization") token: String,
        @Query("responsibleId") responsibleId: Int?
    ): Call<List<UserResponse>>

    @GET("getStudentsByClass/")
    fun getStudentsByClass(
        @Header("Authorization") token: String,
        @Query("classId") classId: Int?
    ): Call<List<UserResponse>>

    @GET("getResponsible/")
    fun getResponsible(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): Call<UserResponse>

    @GET("searchUserByName/")
    fun searchUserByName(
        @Header("Authorization") token: String,
        @Query("name") name: String
    ): Call<UserResponse>

    @GET("getAllUsers/")
    fun getAllUsers(
        @Header("Authorization") token: String
    ): Call<List<UserResponse>>

    @GET("getAllStudents/")
    fun getAllStudents(
        @Header("Authorization") token: String
    ): Call<List<UserResponse>>

    @GET("getAllTeachers/")
    fun getAllTeachers(
        @Header("Authorization") token: String
    ): Call<List<UserResponse>>

    @GET("getAllResponsibles/")
    fun getAllResponsibles(
        @Header("Authorization") token: String
    ): Call<List<UserResponse>>

    @GET("getAllAdministrators/")
    fun getAllAdministrators(
        @Header("Authorization") token: String
    ): Call<List<UserResponse>>

    @GET("getAllCoordinators/")
    fun getAllCoordinators(
        @Header("Authorization") token: String
    ): Call<List<UserResponse>>

    @GET("getReportStudents/")
    fun getReportStudents(
        @Header("Authorization") token: String,
        @Query("initialDate") initialDate: String,
        @Query("finalDate") finalDate: String
    ): Call<List<Report>>

    @GET("getReportResponsibles/")
    fun getReportResponsibles(
        @Header("Authorization") token: String,
        @Query("initialDate") initialDate: String,
        @Query("finalDate") finalDate: String
    ): Call<List<Report>>

    @GET("getUser/")
    fun getUser(
        @Header("Authorization") token: String,
        @Query("id") id: Int?
    ): Call<UserResponse>
}