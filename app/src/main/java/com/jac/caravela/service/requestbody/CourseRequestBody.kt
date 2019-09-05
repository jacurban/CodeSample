package com.jac.caravela.service.requestbody

class CourseRequestBody {
    var id: Int? = null
    var name: String? = null
    var description: String? = null

    constructor(name: String?, description: String?){
        this.name = name
        this.description = description
    }

    constructor(id: Int?, name: String?, description: String?){
        this.id = id
        this.name = name
        this.description = description
    }

    constructor(id: Int?){
        this.id = id
    }
}