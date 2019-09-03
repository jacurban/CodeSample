package com.jac.caravela.service.requestbody

data class RegisterPartnerRequestBody (
    val name: String,
    val cnpj: String,
    val email: String,
    val description: String?
)

data class EditPartnerRequestBody (
    val id: Int,
    val name: String,
    val cnpj: String,
    val email: String,
    val description: String?
)