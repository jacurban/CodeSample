package com.jac.caravela.service.apis

import com.jac.caravela.model.Course
import com.jac.caravela.service.requestbody.CourseRequestBody
import com.jac.caravela.service.responsebody.BaseResponse
import retrofit2.Call
import retrofit2.http.*

interface CourseApi {
    @Headers("Content-Type: application/json")
    @POST("registerCourse/")
    fun postRegisterCourse(
        @Header("Authorization") token: String,
        @Body requestBody: CourseRequestBody
    ): Call<BaseResponse>

    @Headers("Content-Type: application/json")
    @POST("editCourse/")
    fun postEditCourse(
        @Header("Authorization") token: String,
        @Body requestBody: CourseRequestBody
    ): Call<Void>

    @Headers("Content-Type: application/json")
    @POST("deleteCourse/")
    fun postDeleteCourse(
        @Header("Authorization") token: String,
        @Body requestBody: CourseRequestBody
    ): Call<Void>

    @GET("getAllCourses/")
    fun getAllCourses(
        @Header("Authorization") token: String
    ): Call<List<Course>>
}