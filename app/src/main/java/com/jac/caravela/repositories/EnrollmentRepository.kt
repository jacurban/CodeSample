package com.jac.caravela.repositories

import android.util.Log
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Enrollment
import com.jac.caravela.model.Report
import com.jac.caravela.service.apis.EnrollmentApi
import com.jac.caravela.service.requestbody.EnrollmentResquestBody
import com.jac.caravela.service.requestbody.RegisterEnrollmentResquestBody
import java.io.IOException
import java.util.*

class EnrollmentRepository(private val enrollmentApi: EnrollmentApi){

    fun postRegisterEnrollment(studentId: Int, classId:Int?): Pair<Boolean, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = enrollmentApi.postRegisterEnrollment(
            token, RegisterEnrollmentResquestBody(studentId, classId)
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

    fun postRejectEnrollment(enrollmentId:Int?): Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val call = enrollmentApi.postRejectEnrollment(
            token, EnrollmentResquestBody(enrollmentId)
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

    fun postAcceptEnrollment(enrollmentId:Int?): Pair<Boolean, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = enrollmentApi.postAcceptEnrollment(
            token, EnrollmentResquestBody(enrollmentId)
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

    fun getAllEnrollments(): Triple<Boolean, Int?, List<Enrollment>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = enrollmentApi.getAllEnrollments(token)
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

    fun getReportEnrollments(initialDate: String, finalDate: String):Triple<Boolean, Int?, List<Report>?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = enrollmentApi.getReportEnrollments(token, initialDate, finalDate)
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

    fun deleteEnrollment(id: Int?): Pair<Boolean, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = enrollmentApi.deleteEnrollment(token, EnrollmentResquestBody(id))
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

}