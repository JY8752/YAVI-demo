package com.example.yavi.demo.basic

import com.example.yavi.demo.basic.*
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.util.*

internal class UserTest : FunSpec({
    test("when name is not empty and email is empty, isInvalid") {
        val user = User(0L, "user", "")
        val violations = userValidator.validate(user)

        violations.isValid shouldBe false
        violations.size shouldBe 1
        println(violations[0].message())
    }

    test("when name is empty and email is empty, isValid") {
        val user = User(0L, "", "")
        val violations = userValidator.validate(user)

        violations.isValid shouldBe true
        violations.size shouldBe 0
    }

    context("test group pattern") {
        data class TestPattern(val group: Group, val isValid: Boolean)
        val user = User(null, "user", "test@test.com")
        withData(
            nameFn = { "when group: ${it.group}, isValid: ${it.isValid}" },
            TestPattern(Group.CREATE, true), // create の時はid はnull
            TestPattern(Group.UPDATE, false), // updateの時はidはnot null
            TestPattern(Group.DELETE, true) // deleteは検証なし
        ) { (group, isValid) ->
            val violations = userGroupValidator.validate(user, group)
            violations.isValid shouldBe isValid
        }
    }

    test("test en message") {
        val user = User(null, "user", "test@test.com")
        val violations = userMessageValidator.validate(user, Locale.ENGLISH)

        violations.isValid shouldBe false
        violations[0].message() shouldBe "test id require!"
    }

    test("test ja message") {
        val user = User(null, "user", "test@test.com")
        val violations = userMessageValidator.validate(user, Locale.JAPAN)

        violations.isValid shouldBe false
        violations[0].message() shouldBe "test id 必須です！！"
    }
})
