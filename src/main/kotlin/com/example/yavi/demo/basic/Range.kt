package com.example.yavi.demo.basic

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.core.CustomConstraint

data class Range(val from: Int, val to: Int)

val rangeValidator = ValidatorBuilder.of<Range>()
    .constraint(Range::from, "from") { it.greaterThan(0) }
    .constraint(Range::to, "to") { it.greaterThan(0) }
    .constraintOnTarget({ it.to > it.from }, "to", "to.isGreaterThanFrom", "\"to\" must be greater than \"from\"")
    .build()

object RangeConstraint : CustomConstraint<Range> {
    override fun defaultMessageFormat(): String {
        return "\"to\" must be greater than \"from\""
    }

    override fun messageKey(): String {
        return "to.isGreaterThanFrom"
    }

    override fun test(t: Range?): Boolean {
        return t?.let { it.to > it.from } ?: false
    }
}

val rangeValidator2 = ValidatorBuilder.of<Range>()
    .constraint(Range::from, "from") { it.greaterThan(0) }
    .constraint(Range::to, "to") { it.greaterThan(0) }
    .constraintOnTarget(RangeConstraint, "to")
    .build()
