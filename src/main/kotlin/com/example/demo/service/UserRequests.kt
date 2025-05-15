package com.example.demo.service

data class UserCreateRequest(
    val name: String,
    val age: Int,
)

data class UserUpdateRequest(
    val name: String? = null,
    val age: Int? = null,
)