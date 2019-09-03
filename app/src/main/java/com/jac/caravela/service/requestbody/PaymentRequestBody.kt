package com.jac.caravela.service.requestbody

data class RegisterPaymentRequestBody (
    val enrollmentId: Int,
    val loggedUserId: Int
)
