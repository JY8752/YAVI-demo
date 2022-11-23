package com.example.yavi.demo.arguments

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.mock.web.MockHttpServletRequest

internal class PersonTest : FunSpec({
    test("test") {
        val validated = personArgumentsValidator.validate("Jone Doe", "jdoe@example.com", 30)
        validated.isValid shouldBe true
        validated.value() shouldBe Person("Jone Doe", "jdoe@example.com", 30)
    }
    test("test HttpServletRequest") {
        val request = MockHttpServletRequest().also {
            it.setParameter("name", "user")
            it.setParameter("email", "test@test.com")
            it.setParameter("age", "32")
        }
        requestPersonValidator.validate(request).also {
            it.isValid shouldBe true
            it.value() shouldBe Person("user", "test@test.com", 32)
        }
    }
})
