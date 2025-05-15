package com.example.demo.controller

data class UserCreateRequestForm(val name: String, val age: Int, )
data class UserUpdateRequestForm(val name: String? = null, val age: Int? = null, )

data class UserCreateResponse(val id: Long)
data class UserResponse(
    val id: Long,
    val name: String,
    val age: Int,
)