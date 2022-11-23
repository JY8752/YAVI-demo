package com.example.yavi.demo.http.infrastructure

import com.example.yavi.demo.http.domain.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl : UserRepository {
    override fun create(name: String, email: String, age: Int): UserEntity {
        return UserEntity(0L, name, email, age)
    }

    override fun update(name: String?, email: String?, age: Int?): UserEntity {
        return UserEntity(0L, name ?: "", email ?: "", age ?: 0)
    }

    override fun get(id: Long): UserEntity? {
        return null
    }

    override fun delete(id: Long) {
    }
}