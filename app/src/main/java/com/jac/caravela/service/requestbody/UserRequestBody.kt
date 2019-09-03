package com.jac.caravela.service.requestbody


data class LoginRequestBody(
    val email: String,
    val password: String
)

class UserRequestBody {
    var id: Int? = null
    var name: String? = null
    var birthDate: String? = null
    var cpf: String? = null
    var telephone: String? = null
    var email: String? = null
    var password: String? = null
    var responsibleId: Int? = null

    constructor(name: String?, birth: String?, cpf: String?, phone: String?, email: String?) {
        this.name = name
        this.birthDate = birth
        this.cpf = cpf
        this.telephone = phone
        this.email = email
    }

    constructor(id:Int?, name: String?, birth: String?, cpf: String?, phone: String?, email: String?) {
        this.id = id
        this.name = name
        this.birthDate = birth
        this.cpf = cpf
        this.telephone = phone
        this.email = email
    }

    constructor(name: String?, birth: String?, cpf: String?, phone: String?, email: String?, password: String?) {
        this.name = name
        this.birthDate = birth
        this.cpf = cpf
        this.telephone = phone
        this.email = email
        this.password = password
    }

    constructor(name: String?, birth: String?, cpf: String?, phone: String?, email: String?, responsibleId: Int?) {
        this.name = name
        this.birthDate = birth
        this.cpf = cpf
        this.telephone = phone
        this.email = email
        this.responsibleId = responsibleId
    }

    constructor(id: Int?, name: String?, birth: String?, cpf: String?, phone: String?, email: String?, password: String?) {
        this.id = id
        this.name = name
        this.birthDate = birth
        this.cpf = cpf
        this.telephone = phone
        this.email = email
        this.password = password
    }

    constructor(id: Int?){
        this.id = id
    }
}