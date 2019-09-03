package com.jac.caravela.service.requestbody

import com.jac.caravela.model.Lesson
import java.util.*

class ClassRequestBody {

    var id: Int? = null
    var name: String? = null
    var localname: String? = null
    var courseId: Int? = null
    var teacherId: Int? = null
    var latitude: String? = null
    var longitude: String? = null
    var lessons: List<Lesson>? = null
    var price: String? = null
    var initialDate: String? = null

    constructor(id: Int?){
        this.id = id
    }

    constructor(
        id: Int?,
        name: String?,
        localname: String?,
        courseId: Int?,
        teacherId: Int?,
        latitude: String?,
        longitude: String?,
        lessons: List<Lesson>?,
        price: String?,
        initialDate: String?
    ){
        this.id = id
        this.name = name
        this.localname = localname
        this.courseId = courseId
        this.teacherId = teacherId
        this.latitude = latitude
        this.longitude = longitude
        this.lessons = lessons
        this.price = price
        this.initialDate = initialDate

    }

    constructor(
        name: String?,
        localname: String?,
        courseId: Int?,
        teacherId: Int?,
        latitude: String?,
        longitude: String?,
        lessons: List<Lesson>?,
        price: String?,
        initialDate: String?
    ){
        this.name = name
        this.localname = localname
        this.courseId = courseId
        this.teacherId = teacherId
        this.latitude = latitude
        this.longitude = longitude
        this.lessons = lessons
        this.price = price
        this.initialDate = initialDate

    }
}