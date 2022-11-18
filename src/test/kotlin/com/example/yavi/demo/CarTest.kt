package com.example.yavi.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

internal class CarTest : FunSpec({
    test("manufacturerIsNull") {
        val car = Car(null, "DD-AB-123", 4)
        val violations = Car.validator.validate(car)

        violations.isValid shouldBe false
        violations.size shouldBe 1
        violations[0].message() shouldBe """
            "manufacturer" must not be null
        """.trimIndent()
    }

    test("licensePlateTooShort") {
        val car = Car("Morris", "D", 4)
        val violations = Car.validator.validate(car)

        violations.isValid shouldBe false
        violations.size shouldBe 1
        violations[0].message() shouldBe """
            The size of "licensePlate" must be greater than or equal to 2. The given size is 1
        """.trimIndent()
    }

    test("seatCountTooLow") {
        val car = Car("Morris", "DD-AB-123", 1)
        val violations = Car.validator.validate(car)

        violations.isValid shouldBe false
        violations.size shouldBe 1
        violations[0].message() shouldBe """
            "seatCount" must be greater than or equal to 2
        """.trimIndent()
    }

    test("carIsValid") {
        val car = Car("Morris", "DD-AB-123", 2)
        val violations = Car.validator.validate(car)

        violations.isValid shouldBe true
        violations.size shouldBe 0
    }
})
