package com.example.yavi.demo.http.presentation

import am.ik.yavi.arguments.ArgumentsValidators
import am.ik.yavi.builder.IntegerValidatorBuilder
import am.ik.yavi.builder.StringValidatorBuilder
import com.example.yavi.demo.http.domain.User

data class CreateUserRequest(val name: String?, val email: String?, val age: Int?) {
    companion object {
        private val nameValidator = StringValidatorBuilder
            .of("name") { it.notBlank().greaterThan(0).lessThanOrEqual(50) }
            .build()
            .compose<CreateUserRequest> { it.name }

        private val emailValidator = StringValidatorBuilder
            .of("email") { it.notBlank().lessThanOrEqual(100) }
            .build()
            .compose<CreateUserRequest> { it.email }

        private val ageValidator = IntegerValidatorBuilder
            .of("age") { it.greaterThanOrEqual(0).lessThanOrEqual(100) }
            .build()
            .compose<CreateUserRequest> { it.age }

        val validator = ArgumentsValidators.combine(nameValidator, emailValidator, ageValidator)
            .apply { name, email, age -> User(name!!, email!!, age!!) }
    }
}
