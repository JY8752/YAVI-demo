package com.example.yavi.demo

import am.ik.yavi.builder.ValidatorBuilder

class Car(
    private val manufacturer: String?,
    private val licensePlate: String?,
    private val seatCount: Int
) {
    companion object {
        val validator = ValidatorBuilder.of<Car>()
            .constraint(Car::manufacturer, "manufacturer") { it.notNull() }
            .constraint(Car::licensePlate, "licensePlate") { it.notNull().greaterThanOrEqual(2).lessThanOrEqual(14) }
            .constraint(Car::seatCount, "seatCount") { it.greaterThanOrEqual(2) }
            .build()
    }
}
