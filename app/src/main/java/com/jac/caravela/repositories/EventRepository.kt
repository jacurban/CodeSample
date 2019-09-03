package com.jac.caravela.repositories

import android.util.Log
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Evento
import com.jac.caravela.service.apis.EventApi
import com.jac.caravela.service.requestbody.BaseRequestBody
import com.jac.caravela.service.requestbody.EventRequestBody
import java.io.IOException

class EventRepository(private val eventApi: EventApi) {

    fun postRegisterEvent(
        name: String, date: String, reference: String, latitude: String, longitude: String, description: String
    ): Pair<Boolean, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = eventApi.postRegisterEvent(
            token,
            EventRequestBody(
                name = name,
                date = date,
                local = reference,
                latitude = latitude,
                longitude = longitude,
                description = description
            )
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

    fun postEditEvent(
        id: Int,
        name: String,
        date: String,
        reference: String,
        latitude: String,
        longitude: String,
        description: String
    ): Pair<Boolean, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = eventApi.postEditEvent(
            token,
            EventRequestBody(
                id = id,
                name = name,
                date = date,
                local = reference,
                latitude = latitude,
                longitude = longitude,
                description = description
            )
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

    fun postDeleteEvent(id: Int): Pair<Boolean, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = eventApi.postDeleteEvent(
            token,
            BaseRequestBody(id)
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

    fun getAllEvents(): Triple<Boolean, Int?, List<Evento>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = eventApi.getAllEvents(token)
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
        } catch (e: Exception) {
            Log.e("API-CALL", e.message)
        }
        return Triple(false, R.string.error_unspecified, null)
    }

}