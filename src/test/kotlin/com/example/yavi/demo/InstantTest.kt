package com.example.yavi.demo

import am.ik.yavi.builder.ValidatorBuilder
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

internal class InstantTest : FunSpec({
    data class TestTime(val time: Instant)

    // constraint
    val constraint = InstantRangeConstraint(
        start = LocalDateTime.of(2022, 11, 20, 9, 0).toInstant(ZoneOffset.UTC),
        end = Instant.from(LocalDateTime.of(2022, 11, 20, 18, 0).toInstant(ZoneOffset.UTC))
    )

    // validator
    val validator = ValidatorBuilder.of<TestTime>()
        .constraint(TestTime::time, "time") { it.predicate(constraint) }
        .build()

    context("test instant range pattern") {
        data class TestPattern(val time: Instant, val isValid: Boolean)
        withData(
            nameFn = { "when time: ${it.time}, isValid: ${it.isValid}" },
            TestPattern(LocalDateTime.of(2022, 11, 20, 9, 0).toInstantOffsetUTC(), false),
            TestPattern(LocalDateTime.of(2022, 11, 20, 10, 0).toInstantOffsetUTC(), true),
            TestPattern(LocalDateTime.of(2022, 11, 20, 18, 0).toInstantOffsetUTC(), false),
            TestPattern(LocalDateTime.of(2022, 11, 20, 19, 0).toInstantOffsetUTC(), false)
        ) { (time, isValid) ->
            val t = TestTime(time)
            validator.validate(t).isValid shouldBe isValid
        }
    }
    test("test invalid testTime") {
        val t = TestTime(LocalDateTime.of(2022, 11, 20, 8, 0).toInstantOffsetUTC())
        val violations = validator.validate(t)

        violations.isValid shouldBe false
        violations.size shouldBe 1
        violations[0].message() shouldBe """
            Instant value "time" must be between "2022-11-20T09:00:00Z" and "2022-11-20T18:00:00Z".
        """.trimIndent()
    }
}) {
    companion object {
        fun LocalDateTime.toInstantOffsetUTC(): Instant {
            return this.toInstant(ZoneOffset.UTC)
        }
    }
}
