package com.jac.caravela.scenes.turmacadastro

import com.jac.caravela.core.App
import com.jac.caravela.model.Lesson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TurmaCadastroPresenter(val view: TurmaCadastro.View) : TurmaCadastro.Presenter() {
    override fun postEditClass(
        id: Int,
        name: String,
        localname: String,
        courseId: Int,
        teacherId: Int,
        latitude: String,
        longitude: String,
        lessons: List<Lesson>,
        price: String,
        initialDate: String?
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.classRepository.postEditClass(
                    id, name, localname, courseId, teacherId, latitude, longitude, lessons, price, initialDate
                )
            }
            if (response.first)
                view.successfulEdit()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun postRegisterClass(
        name: String,
        localname: String,
        courseId: Int,
        teacherId: Int,
        latitude: String,
        longitude: String,
        lessons: List<Lesson>,
        price: String,
        initialDate: String?
    ) {
        job = launch {
            val response = withContext(Dispatchers.IO) {
                App.classRepository.postRegisterClass(
                    name, localname, courseId, teacherId, latitude, longitude, lessons, price, initialDate
                )
            }
            if (response.first)
                view.successfulRegister()
            else
                view.unsuccessfulRegister(response.second)
        }
    }

    override fun getAllTeachers() {
        job = launch {
            val response = withContext(Dispatchers.IO) { App.userRepository.getAllTeachers() }
            if (response.first)
                response.third?.let { view.successfulGetAll(it) }
            else
                view.unsuccessfulRegister(response.second)
        }
    }
}