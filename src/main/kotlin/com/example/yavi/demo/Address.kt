package com.example.yavi.demo

import am.ik.yavi.builder.ValidatorBuilder

data class Country(val name: String)
data class City(val name: String)

class Address(val country: Country, val city: City) {
    companion object {
        private val countryValidator = ValidatorBuilder.of<Country>().constraint(Country::name, "name") { it.notBlank().lessThanOrEqual(20) }.build()
        private val cityValidator = ValidatorBuilder.of<City>().constraint(City::name, "name") { it.notBlank().lessThanOrEqual(100) }.build()
        val validator = ValidatorBuilder.of<Address>()
            .nest(Address::country, "country", countryValidator)
            .nest(Address::city, "city", cityValidator)
            .build()
    }
}
