package com.example.demo.service

import com.example.demo.domain.User
import com.example.demo.domain.UserEntity
import com.example.demo.domain.UserId
import org.jetbrains.exposed.v1.core.SqlExpressionBuilder.eq
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.update
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@Transactional
class UserService {

    // read user by user primary key
    fun findUserById(id: UserId): User? {
        // Use Exposed dsl without `transaction { }`
        return UserEntity.selectAll().where { UserEntity.id eq id.value }.firstOrNull()?.let {
            User(
                id = UserId(it[UserEntity.id].value),
                name = it[UserEntity.name],
                age = it[UserEntity.age],
            )
        }
    }

    // create user
    fun create(request: UserCreateRequest): UserId {
        val id = UserEntity.insertAndGetId {
            it[name] = request.name
            it[age] = request.age
        }

        return UserId(id.value)
    }

    // update user
    fun update(id: Long, request: UserUpdateRequest) {
        UserEntity.update({ UserEntity.id eq id }) {
            request.name?.let { name -> it[UserEntity.name] = name }
            request.age?.let { age -> it[UserEntity.age] = age }
        }
    }

    // delete user
    fun delete(id: UserId) {
        UserEntity.deleteWhere { UserEntity.id eq id.value }
    }
}

data class UserCreateRequest(val name: String, val age: Int)
data class UserUpdateRequest(val name: String? = null, val age: Int? = null)