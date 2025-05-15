package com.example.demo.domain

import org.jetbrains.exposed.v1.core.dao.id.LongIdTable

// users table definition
object UserEntity : LongIdTable(name = "DEMO_USERS") {
    val name = varchar("name", length = 50)
    val age = integer("age")
}