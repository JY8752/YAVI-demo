package com.example.yavi.demo

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.core.ConstraintGroup

data class User(val id: Long?, val name: String, val email: String)

val userValidator = ValidatorBuilder.of<User>()
    .constraintOnCondition({ user, _ -> user.name.isNotEmpty() }) {
        it.constraint(User::email, "email") { c ->
            c.email().notEmpty()
        }
    }
    .build()

// enum class Group : ConstraintGroup {
//    CREATE, UPDATE, DELETE;
//
//    @JvmName("test")
//    override fun name(): String {
//        TODO("Not yet implemented")
//    }
// }
//
// interface Test {
//    fun name(): String
// }
//
// enum class TestEnum : Test {
//    CREATE, DELETE;
// }

sealed class Group : ConstraintGroup {
    object CREATE : Group()
    object UPDATE : Group()
    object DELETE : Group()
    override fun name() = this.toString()
}

val userGroupValidator = ValidatorBuilder.of<User>()
    .constraintOnGroup(Group.CREATE) { it.constraint(User::id, "id") { c -> c.isNull } }
    .constraintOnGroup(Group.UPDATE) { it.constraint(User::id, "id") { c -> c.notNull() } }
    .build()

val userMessageValidator = ValidatorBuilder.of<User>()
    .messageFormatter(ResourceBundleMessageFormatter)
    .constraint(User::id, "id") { it.notNull() }
    .build()