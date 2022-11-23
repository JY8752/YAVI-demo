package com.example.yavi.demo.arguments

import am.ik.yavi.arguments.Arguments3Validator
import am.ik.yavi.builder.ArgumentsValidatorBuilder
import am.ik.yavi.builder.IntegerValidatorBuilder
import am.ik.yavi.builder.StringValidatorBuilder

data class User(val email: String?, val name: String?)
class UserService {
    fun createUser(email: String?, name: String?) = User(email, name)
}

val userServiceValidator: Arguments3Validator<UserService?, String?, String?, User> = ArgumentsValidatorBuilder
    .of { service: UserService?, name: String?, email: String? -> service!!.createUser(name, email) }
    .builder {
        it._object({ arg -> arg.arg1() }, "userService") { c -> c.notNull() }
            ._string({ arg -> arg.arg2() }, "email") { c -> c.email() }
            ._string({ arg -> arg.arg3() }, "name") { c -> c.notNull() }
    }.build()

data class Name(val value: String)
data class Email(val value: String)
data class Age(val value: Int)

val nameValidator = StringValidatorBuilder
    .of("name") { it.notBlank().lessThanOrEqual(100) }
    .build()
    .andThen { name -> Name(name) }

val emailValidator = StringValidatorBuilder
    .of("email") { it.notBlank().lessThanOrEqual(100).email() }
    .build()
    .andThen { email -> Email(email) }

val ageValidator = IntegerValidatorBuilder
    .of("age") { it.greaterThan(0).lessThan(200) }
    .build()
    .andThen { age -> Age(age) }

