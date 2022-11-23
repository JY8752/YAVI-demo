package com.example.yavi.demo.http.application

import com.example.yavi.demo.http.domain.User
import com.example.yavi.demo.http.domain.UserRepository
import com.example.yavi.demo.http.infrastructure.UserEntity
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun create(request: User): User {
        return this.userRepository.create(request.name, request.email, request.age).toModel()
    }

    private fun UserEntity.toModel() = User(this.name, this.email, this.age)
}
