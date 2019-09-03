package com.jac.caravela.model

enum class UserType(val value: Int) {
    ADMINISTRATOR(1),
    RESPONSIBLE(2),
    TEACHER(3),
    COORDINATOR(4),
    STUDENT(5);

    companion object {
        fun isAdministrator(type: Int) = type == UserType.ADMINISTRATOR.value
        fun isResponsible(type: Int) = type == UserType.RESPONSIBLE.value
        fun isTeacher(type: Int) = type == UserType.TEACHER.value
        fun isCoordinator(type: Int) = type == UserType.COORDINATOR.value
        fun isStudent(type: Int) = type == UserType.STUDENT.value
    }
}