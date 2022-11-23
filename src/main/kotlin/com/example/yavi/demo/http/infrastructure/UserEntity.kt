package com.example.yavi.demo.http.infrastructure

import java.time.LocalDateTime

// 手抜きで仮実装
data class UserEntity(
    val id: Long? = null,
    val name: String,
    val email: String,
    val age: Int,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
