package com.example.yavi.demo.http.presentation

import com.example.yavi.demo.http.application.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/create")
    fun create(@RequestBody request: CreateUserRequest): Response {
        val validated = CreateUserRequest.validator.validate(request)
        return if (validated.isValid) {
            val created = this.userService.create(validated.value())
            Response.success(created)
        } else {
            Response.error(validated.errors())
        }
    }
}
