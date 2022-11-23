package com.example.yavi.demo.http.domain

import com.example.yavi.demo.http.infrastructure.UserEntity

interface UserRepository {
    fun create(name: String, email: String, age: Int): UserEntity
    fun update(name: String?, email: String?, age: Int?): UserEntity
    fun get(id: Long): UserEntity?
    fun delete(id: Long)
}
