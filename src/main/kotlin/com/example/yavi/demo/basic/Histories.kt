package com.example.yavi.demo.basic

import am.ik.yavi.builder.ValidatorBuilder

data class History(val revision: Int?)

data class Histories(val value: List<History>)

val historyValidator = ValidatorBuilder.of<History>()
    .constraint(History::revision, "revision") { it.notNull().greaterThanOrEqual(1) }
    .build()

val historiesValidator = ValidatorBuilder.of<Histories>()
    .forEach(Histories::value, "histories", historyValidator)
    .build()
