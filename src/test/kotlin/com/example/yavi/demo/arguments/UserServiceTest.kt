package com.example.yavi.demo.arguments

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class UserServiceTest : FunSpec({
    test("test createUser") {
        val service = UserService()
        val validated = userServiceValidator.validate(service, "jdoe@example.com", "John Doe")
        validated.isValid shouldBe true
        validated.value() shouldBe User("jdoe@example.com", "John Doe")
    }
//    context("single value test") {
//        data class TestPattern<T, R>(val validator: Arguments1Validator<T, R>, val value: T, val isValid: Boolean)
//        withData(
//            nameFn = { "" },
//            TestPattern<String, Name>(nameValidator, "Jone Doe", true),
//            TestPattern<String, Email>(emailValidator, "jdoe@example.com", true),
//            TestPattern<Int, Age>(ageValidator, 30, true),
//        ) { (validator, value, isValid) ->
//            validator.validate(value as String).isValid shouldBe isValid
//            nameValidator.validate(value)
//        }
//    }
    test("test single value validator") {
        nameValidator.validate("Jone Doe").also {
            it.isValid shouldBe true
            it.value() shouldBe Name("Jone Doe")
        }
        emailValidator.validate("jdoe@example.com").also {
            it.isValid shouldBe true
            it.value() shouldBe Email("jdoe@example.com")
        }
        ageValidator.validate(30).also {
            it.isValid shouldBe true
            it.value() shouldBe Age(30)
        }
    }
    test("test liftList") {
        ageValidator.liftList().validate(listOf(1, 2, 3, 4, 5)).also {
            it.isValid shouldBe true
            it.value() shouldBe listOf(Age(1), Age(2), Age(3), Age(4), Age(5))
        }
        ageValidator.liftList().validate(listOf(-1, 0, 1)).also {
            it.isValid shouldBe false
            it.errors().size shouldBe 2
        }
    }
})