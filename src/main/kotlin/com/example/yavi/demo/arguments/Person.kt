package com.example.yavi.demo.arguments

import am.ik.yavi.arguments.Arguments3Validator
import am.ik.yavi.arguments.ArgumentsValidators
import am.ik.yavi.builder.ArgumentsValidatorBuilder
import am.ik.yavi.builder.IntegerValidatorBuilder
import am.ik.yavi.builder.StringValidatorBuilder
import javax.servlet.http.HttpServletRequest

data class Person(val name: String?, val email: String?, val age: Int?)

val personArgumentsValidator: Arguments3Validator<String?, String?, Int?, Person> = ArgumentsValidatorBuilder
    .of { name: String?, email: String?, age: Int? -> Person(name, email, age) }
    .builder {
        it._string({ arg -> arg.arg1() }, "name") { c -> c.notBlank().lessThanOrEqual(100) }
            ._string({ arg -> arg.arg2() }, "email") { c -> c.notBlank().lessThanOrEqual(100).email() }
            ._integer({ arg -> arg.arg3() }, "age") { c -> c.greaterThanOrEqual(0).lessThan(200) }
    }.build()

val personNameValidator = StringValidatorBuilder
    .of("name") { it.notBlank().lessThanOrEqual(100) }
    .build()

val personEmailValidator = StringValidatorBuilder
    .of("email") { it.notBlank().lessThanOrEqual(100) }
    .build()

val personAgeValidator = IntegerValidatorBuilder
    .of("age") { it.greaterThanOrEqual(0).lessThanOrEqual(100) }
    .build()

val personValidator = ArgumentsValidators
    .split(personNameValidator, personEmailValidator, personAgeValidator)
    .apply { name, email, age -> Person(name, email, age) }

val requestNameValidator = personNameValidator.compose<HttpServletRequest> {
    it.getParameter("name")
}

val requestEmailValidator = personEmailValidator.compose<HttpServletRequest> {
    it.getParameter("email")
}

val requestAgeValidator = personAgeValidator.compose<HttpServletRequest> {
    it.getParameter("age").toInt()
}

val requestPersonValidator = ArgumentsValidators.combine(requestNameValidator, requestEmailValidator, requestAgeValidator)
    .apply { name, email, age -> Person(name, email, age) }
