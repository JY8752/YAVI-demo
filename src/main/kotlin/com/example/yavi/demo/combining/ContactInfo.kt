package com.example.yavi.demo.combining

import am.ik.yavi.arguments.ArgumentsValidators
import am.ik.yavi.builder.validator

data class Email(val value: String)
data class PhoneNumber(val value: String)
data class ContactInfo(val email: Email?, val phoneNumber: PhoneNumber?)

val emailValidator = validator<Email> {
    (Email::value)("email") {
        notBlank()
        email()
    }
}

val phoneNumberValidator = validator<PhoneNumber> {
    (PhoneNumber::value)("phoneNumber") {
        notBlank()
        pattern("[0-9\\-]+")
    }
}

val contactInfoValidator = ArgumentsValidators
    .split(emailValidator.applicative(), phoneNumberValidator.applicative())
    .apply { em, ph -> ContactInfo(em, ph) }
