package com.jac.caravela.repositories

import android.util.Log
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Payment
import com.jac.caravela.model.Report
import com.jac.caravela.service.apis.PaymentApi
import com.jac.caravela.service.requestbody.RegisterPaymentRequestBody
import java.io.IOException

class PaymentRepository (private val paymentApi: PaymentApi){

    fun postRegisterPayment(enrollmentId: Int, loggedUserId: Int): Pair<Boolean, Int?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = paymentApi.postRegisterPayment(token,
            RegisterPaymentRequestBody(enrollmentId, loggedUserId)
        )
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Pair(true, null)
            } else {
                Log.d("API-CALL", "postLogin Response Error: ${response.errorBody()?.toString()}")
                if (response.code() == 401)
                    return Pair(false, R.string.error_invalid_token)
            }
        } catch (e: IOException) {
            Log.e("API-CALL", e.message)
            Pair(false, R.string.error_network)
        } catch (e: Exception) {
            Log.e("API-CALL", e.message)
        }
        return Pair(false, R.string.error_unspecified)
    }

    fun getPaymentsByResponsible(responsibleId: Int?):Triple<Boolean, Int?, List<Payment>?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = paymentApi.getPaymentsByResponsible(token, responsibleId)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body())
            } else {
                Log.d("API-CALL", "postLogin Response Error: ${response.errorBody()?.toString()}")
                if (response.code() == 401)
                    return Triple(false, R.string.error_invalid_token, null)
            }
        } catch (e: IOException) {
            Log.e("API-CALL", e.message)
            Triple(false, R.string.error_network, null)
        } catch (e: RuntimeException) {
            Log.e("API-CALL", e.message)
        }
        return Triple(false, R.string.error_unspecified, null)
    }

    fun getPaymentsByEnrollment(enrollmentId: Int?):Triple<Boolean, Int?, List<Payment>?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = paymentApi.getPaymentsByEnrollment(token, enrollmentId)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body())
            } else {
                Log.d("API-CALL", "postLogin Response Error: ${response.errorBody()?.toString()}")
                if (response.code() == 401)
                    return Triple(false, R.string.error_invalid_token, null)
            }
        } catch (e: IOException) {
            Log.e("API-CALL", e.message)
            Triple(false, R.string.error_network, null)
        } catch (e: RuntimeException) {
            Log.e("API-CALL", e.message)
        }
        return Triple(false, R.string.error_unspecified, null)
    }

}