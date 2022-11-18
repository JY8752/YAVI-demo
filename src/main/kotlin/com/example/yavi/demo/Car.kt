package com.example.yavi.demo

import am.ik.yavi.builder.ValidatorBuilder

class Car(
    private val manufacturer: String?,
    private val licensePlate: String?,
    private val seatCount: Int
) {
    companion object {
        val validator = ValidatorBuilder.of<Car>()
            .constraint(Car::manufacturer, "manufacturer") { c -> c.notNull() }
            .constraint(Car::licensePlate, "licensePlate") { c -> c.notNull().greaterThanOrEqual(2).lessThanOrEqual(14) }
            .constraint(Car::seatCount, "seatCount") { c -> c.greaterThanOrEqual(2) }
            .build()
    }
}
