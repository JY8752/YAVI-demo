package com.example.yavi.demo.combining

import am.ik.yavi.core.ConstraintViolationsException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import org.springframework.http.HttpStatus

internal class ContactInfoTest : FunSpec({
    test("test email") {
        val email = Email("test@test.com")
        val invalidEmail = Email("test.com")
        val emailValidated = emailValidator.applicative().validate(email)
        val emailValidated2 = emailValidator.applicative().validate(invalidEmail)

        emailValidated.isValid shouldBe true
        emailValidated.value() shouldBe email

        emailValidated2.isValid shouldBe false
        shouldThrow<ConstraintViolationsException> {
            emailValidated2.orElseThrow { ConstraintViolationsException(it) }
        }

        emailValidated.fold({ HttpStatus.BAD_REQUEST }) { HttpStatus.OK } shouldBe HttpStatus.OK
        emailValidated2.fold({ HttpStatus.BAD_REQUEST }) { HttpStatus.OK } shouldBe HttpStatus.BAD_REQUEST
    }
    test("test combining") {
        // given
        val email = Email("")
        val phoneNumber = PhoneNumber("")

        val emailValidated = emailValidator.applicative().validate(email)
        val phoneNumberValidated = phoneNumberValidator.applicative().validate(phoneNumber)

        val contactInfoValidated = emailValidated.combine(phoneNumberValidated).apply { em, ph -> ContactInfo(em, ph) }
        val contactInfoValidated2 = contactInfoValidator.validate(email, phoneNumber)

        // expected
        contactInfoValidated.isValid shouldBe false
        var errors = contactInfoValidated.errors()
        errors.size shouldBe 3

        errors[0].message() shouldBe """
            "email" must not be blank
        """.trimIndent()

        errors[1].message() shouldBe """
            "phoneNumber" must not be blank
        """.trimIndent()

        errors[2].message() shouldBe """
            "phoneNumber" must match [0-9\-]+
        """.trimIndent()

        contactInfoValidated2.isValid shouldBe false
        errors = contactInfoValidated2.errors()
        errors.size shouldBe 3

        errors[0].message() shouldBe """
            "email" must not be blank
        """.trimIndent()

        errors[1].message() shouldBe """
            "phoneNumber" must not be blank
        """.trimIndent()

        errors[2].message() shouldBe """
            "phoneNumber" must match [0-9\-]+
        """.trimIndent()
    }
    test("test valid contactInfo") {
        // given
        val email = Email("test@test.com")
        val phoneNumber = PhoneNumber("000-0000-0000")

        val emailValidated = emailValidator.applicative().validate(email)
        val phoneNumberValidated = phoneNumberValidator.applicative().validate(phoneNumber)

        val contactInfoValidated = emailValidated.combine(phoneNumberValidated).apply { em, ph -> ContactInfo(em, ph) }

        // expected
        contactInfoValidated.isValid shouldBe true
        contactInfoValidated.value() shouldBe ContactInfo(email, phoneNumber)
    }
})