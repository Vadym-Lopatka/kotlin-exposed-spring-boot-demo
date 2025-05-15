package com.example.demo.controller

import com.example.demo.domain.UserId
import com.example.demo.service.UserCreateRequest
import com.example.demo.service.UserService
import com.example.demo.service.UserUpdateRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long): ResponseEntity<UserResponse> {
        val user = userService.findUserById(UserId(id))

        return if (user != null) {
            ResponseEntity.ok(
                UserResponse(
                    id = user.id.value,
                    name = user.name,
                    age = user.age,
                )
            )
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun create(@RequestBody form: UserCreateRequestForm): ResponseEntity<UserCreateResponse> {
        val request = UserCreateRequest(name = form.name, age = form.age)
        val userId = userService.create(request)

        return ResponseEntity.ok(UserCreateResponse(id = userId.value))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody form: UserUpdateRequestForm): ResponseEntity<Unit> {

        return userService.findUserById(UserId(id))
            ?.let {
                userService.update(id, UserUpdateRequest(name = form.name, age = form.age))
                ResponseEntity.ok().build()
            }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        userService.delete(UserId(id))
        return ResponseEntity.noContent().build()
    }
}


data class UserCreateRequestForm(val name: String, val age: Int)
data class UserUpdateRequestForm(val name: String? = null, val age: Int? = null)

data class UserCreateResponse(val id: Long)
data class UserResponse(val id: Long, val name: String, val age: Int)