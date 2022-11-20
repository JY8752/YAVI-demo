package com.example.yavi.demo

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

internal class RangeTest : FunSpec({
    context("test range constraint pattern") {
        data class TestPattern(val range: Range, val isValid: Boolean)
        withData(
            nameFn = { "when range: ${it.range}, isValid: ${it.isValid}" },
            TestPattern(Range(1, 2), true),
            TestPattern(Range(1, 1), false),
            TestPattern(Range(2, 1), false)
        ) { (range, isValid) ->
            rangeValidator.validate(range).isValid shouldBe isValid
            rangeValidator2.validate(range).isValid shouldBe isValid
        }
    }
})
