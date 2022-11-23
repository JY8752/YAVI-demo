package com.example.yavi.demo.basic

import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

internal class BookTest : FunSpec({
    context("test book isbn pattern") {
        data class TestPattern(val book: Book, val isValid: Boolean)
        withData(
            nameFn = { "when book: ${it.book}, isValid: ${it.isValid}" },
            TestPattern(Book("title", "ISBN978-4-123456-78-9"), true),
            TestPattern(Book("title", "ISBN4-123456-78-9"), false),
            TestPattern(Book("title", ""), false)
        ) { (book, isValid) ->
            bookValidator.validate(book).isValid shouldBe isValid
        }
    }
    test("test not ISBN format") {
        val book = Book("title", "aaaaaa")
        val violations = bookValidator.validate(book)

        violations.isValid shouldBe false
        violations.size shouldBe 1
        violations[0].message() shouldBe """
            "isbn" must be ISBN13 format
        """.trimIndent()
    }
})
