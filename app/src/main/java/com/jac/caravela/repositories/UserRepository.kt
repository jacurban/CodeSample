package com.jac.caravela.repositories

import android.util.Log
import com.jac.caravela.R
import com.jac.caravela.core.App
import com.jac.caravela.model.Report
import com.jac.caravela.model.User
import com.jac.caravela.persistence.UserDAO
import com.jac.caravela.service.apis.UserApi
import com.jac.caravela.service.requestbody.LoginRequestBody
import com.jac.caravela.service.requestbody.UserRequestBody
import java.io.IOException

class UserRepository(
    private val userDAO: UserDAO,
    private val userApi: UserApi
) {
    fun updateLoggedUser(user: User){
        val token = App.user?.token ?: ""
        logout()
        login(user, token)
    }

    fun getLoggedUser() =
        userDAO.get()

    fun logout() {
        App.user = null
        userDAO.delete()
    }

    private fun login(user: User, token: String) {
        App.user = user
        App.user?.token = token
        userDAO.insert(user)
    }

    fun postLogin(email: String, password: String): Pair<Boolean, Int?> {
        val call = userApi.postLogin(LoginRequestBody(email, password))
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                response.body()?.let { login(it.user.asUser, it.token) }
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

    fun postRegisterResponsible(
        name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ):  Pair<Boolean, Int?>  {
        val call = userApi.postRegisterResponsible(
            UserRequestBody(name, birth, cpf, phone, email, password)
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

    fun postRegisterStudent(
        name: String, birth: String, cpf: String, phone: String, email: String
    ):  Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val responsibleId = App.user?.id ?: -1
        val call = userApi.postRegisterStudent(
            token,
            UserRequestBody(name, birth, cpf, phone, email, responsibleId)
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

    fun postRegisterTeacher(
        name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ):  Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postRegisterTeacher(
            token,
            UserRequestBody(name, birth, cpf, phone, email, password)
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

    fun postRegisterAdministrator(
        name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ):  Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postRegisterAdministrator(
            token,
            UserRequestBody(name, birth, cpf, phone, email, password)
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

    fun postRegisterCoordinator(
        name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ):  Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postRegisterCoordinator(
            token,
            UserRequestBody(name, birth, cpf, phone, email, password)
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

    fun postEditResponsible(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String, password: String
    ):  Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postEditResponsible(
            token,
            UserRequestBody(id, name, birth, cpf, phone, email, password)
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

    fun postEditStudent(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ):  Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postEditStudent(
            token,
            UserRequestBody(id, name, birth, cpf, phone, email)
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

    fun postEditTeacher(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ):  Pair<Boolean, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postEditTeacher(
            token,
            UserRequestBody(id, name, birth, cpf, phone, email)
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

    fun postEditAdministrator(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ):  Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postEditAdministrator(
            token,
            UserRequestBody(id, name, birth, cpf, phone, email)
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

    fun postEditCoordinator(
        id: Int?, name: String, birth: String, cpf: String, phone: String, email: String
    ):  Pair<Boolean, Int?>  {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postEditCoordinator(
            token,
            UserRequestBody(id, name, birth, cpf, phone, email)
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

    fun postDeleteUser(id: Int): Pair<Boolean, Int?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.postDeleteUser(token, UserRequestBody(id))
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


    //gets
    fun getStudentByResponsible(responsibleId: Int?): Triple<Boolean, Int?, List<User>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getStudentByResponsible(token, responsibleId)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.map { it.asUser })
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

    fun getStudentsByClass(classId: Int?): Triple<Boolean, Int?, List<User>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getStudentsByClass(token, classId)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.map { it.asUser })
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

    fun getResponsible(id: Int): Triple<Boolean, Int?, User?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getResponsible(token, id)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.asUser)
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
    }  // studentInfo

    fun searchUserByName(name: String): Triple<Boolean, Int?, User?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.searchUserByName(token, name)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.asUser)
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


    // get all's
    fun getAllUsers(): Triple<Boolean, Int?, List<User>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getAllUsers(token)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.map { it.asUser })
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

    fun getAllStudents(): Triple<Boolean, Int?, List<User>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getAllStudents(token)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.map { it.asUser })
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

    fun getAllTeachers(): Triple<Boolean, Int?, List<User>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getAllTeachers(token)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.map { it.asUser })
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

    fun getAllResponsibles(): Triple<Boolean, Int?, List<User>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getAllResponsibles(token)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.map { it.asUser })
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

    fun getAllAdministrators(): Triple<Boolean, Int?, List<User>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getAllAdministrators(token)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.map { it.asUser })
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

    fun getAllCoordinators(): Triple<Boolean, Int?, List<User>?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getAllCoordinators(token)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.map { it.asUser })
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


    fun getReportStudents(initialDate: String, finalDate: String):Triple<Boolean, Int?, List<Report>?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getReportStudents(token, initialDate, finalDate)
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

    fun getReportResponsibles(initialDate: String, finalDate: String):Triple<Boolean, Int?, List<Report>?>{
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getReportResponsibles(token, initialDate, finalDate)
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

    fun getUser(id: Int?): Triple<Boolean, Int?, User?> {
        val token = App.user?.getBearerToken() ?: ""
        val call = userApi.getUser(token, id)
        try {
            val response = call.execute()
            if (response.isSuccessful) {
                Log.d("API-CALL", "postLogin successful: ${response.body()?.toString()}")
                return Triple(true, null, response.body()?.asUser)
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