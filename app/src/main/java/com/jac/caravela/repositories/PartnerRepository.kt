package com.jac.caravela.repositories

import android.util.Log
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Partner
import com.jac.caravela.service.apis.PartnerApi
import com.jac.caravela.service.requestbody.EditPartnerRequestBody
import com.jac.caravela.service.requestbody.RegisterPartnerRequestBody
import java.io.IOException

class PartnerRepository (private val partnerApi: PartnerApi){
    fun postRegisterPartner(name: String, cnpj: String, email: String, description: String?): Pair<Boolean, Int?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = partnerApi.postRegisterPartner(token,
            RegisterPartnerRequestBody(name, cnpj, email, description)
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

    fun postEditPartner(id: Int, name: String, cnpj: String, email: String, description: String?): Pair<Boolean, Int?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = partnerApi.postEditPartner(token,
            EditPartnerRequestBody(id, name, cnpj, email, description)
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

    fun postDeletePartner(id: Int): Pair<Boolean, Int?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = partnerApi.postDeletePartner(token,id)
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

    fun getAllPartner(): Triple<Boolean, Int?, List<Partner>?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = partnerApi.getAllPartner(token)
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