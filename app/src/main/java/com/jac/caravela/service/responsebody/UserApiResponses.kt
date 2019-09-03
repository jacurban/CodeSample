package com.jac.caravela.service.responsebody

data class LoginResponse (
    val user: UserResponse,
    val token: String = ""
): BaseResponse()

