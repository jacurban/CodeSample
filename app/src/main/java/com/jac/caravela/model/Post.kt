package com.jac.caravela.model

data class Post(
    val id: Int,
    val userId: Int,
    val description: String,
    val createDate: String,
    val updateDate: String,
    val classId: Int,
    val userName: String
)