package com.example.yavi.demo.basic

import am.ik.yavi.core.CustomConstraint
import java.time.Instant

class InstantRangeConstraint(private val start: Instant, private val end: Instant) : CustomConstraint<Instant> {
    override fun arguments(violatedValue: Instant?): Array<Any> {
        return arrayOf(this.start, this.end)
    }

    override fun defaultMessageFormat(): String {
        return "Instant value \"{0}\" must be between \"{1}\" and \"{2}\"."
    }

    override fun messageKey(): String {
        return "instant.range"
    }

    override fun test(t: Instant?): Boolean {
        return t?.isAfter(this.start) ?: false && t?.isBefore(this.end) ?: false
    }
}
