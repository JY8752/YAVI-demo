package com.example.yavi.demo.http.presentation

import am.ik.yavi.core.ConstraintViolations
import org.slf4j.LoggerFactory

class Response private constructor(val success: Boolean, val payload: Any? = null, val errors: List<String> = emptyList()) {
    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        fun success(payload: Any) = Response(true, payload)
        fun error(ex: Exception): Response {
            this.logger.error(ex.message, ex)
            return Response(false, errors = listOf(ex.message ?: "unknown error..."))
        }
        fun error(errors: ConstraintViolations): Response {
            val messages = errors.map { it.message() }
            this.logger.error("errors: {}", messages)
            return Response(false, errors = messages)
        }
    }
}
