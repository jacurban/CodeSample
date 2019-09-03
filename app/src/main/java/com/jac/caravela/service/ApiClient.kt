package com.jac.caravela.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jac.caravela.service.apis.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient { // api call

    val userApi: UserApi
    val courseApi: CourseApi
    val classApi: ClassApi
    val eventApi: EventApi
    val messageApi: MessageApi
    val chatApi: ChatApi
    val enrollmentApi: EnrollmentApi
    val postApi: PostApi
    val partnerApi: PartnerApi
    val paymentApi: PaymentApi


    init {
        userApi = buildRetrofitAPI(ApiConstants.USER_BASE_URL)
            .create(UserApi::class.java)
        courseApi = buildRetrofitAPI(ApiConstants.COURSE_BASE_URL)
            .create(CourseApi::class.java)
        classApi = buildRetrofitAPI(ApiConstants.CLASS_BASE_URL)
            .create(ClassApi::class.java)
        eventApi = buildRetrofitAPI(ApiConstants.EVENT_BASE_URL)
            .create(EventApi::class.java)
        messageApi = buildRetrofitAPI(ApiConstants.MESSAGE_BASE_URL)
            .create(MessageApi::class.java)
        chatApi = buildRetrofitAPI(ApiConstants.CHAT_BASE_URL)
            .create(ChatApi::class.java)
        enrollmentApi = buildRetrofitAPI(ApiConstants.ENROLLMENT_BASE_URL)
            .create(EnrollmentApi::class.java)
        postApi = buildRetrofitAPI(ApiConstants.POST_BASE_URL)
            .create(PostApi::class.java)
        partnerApi = buildRetrofitAPI(ApiConstants.PARTNER_BASE_URL)
            .create(PartnerApi::class.java)
        paymentApi = buildRetrofitAPI(ApiConstants.PAYMENT_BASE_URL)
            .create(PaymentApi::class.java)
    }

    private fun buildRetrofitAPI(baseURL: String) = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(buildOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create(buildGson()))
        .build()

    private fun buildOkHttpClient(): OkHttpClient{
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor)
        return client.build()
    }

    private fun buildGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        .create()
}