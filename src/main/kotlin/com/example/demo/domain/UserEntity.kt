package com.example.demo.domain

import org.jetbrains.exposed.v1.core.dao.id.LongIdTable

object UserEntity : LongIdTable() {
    val name = varchar("name", length = 50)
    val age = integer("age")
}