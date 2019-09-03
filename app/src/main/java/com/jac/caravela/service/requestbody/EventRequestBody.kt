package com.jac.caravela.service.requestbody

data class EventRequestBody(
    val id: Int? = null,
    val name: String,
    val date: String,
    val local: String,
    val latitude: String,
    val longitude: String,
    val description: String
)
