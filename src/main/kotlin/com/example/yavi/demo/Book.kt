package com.example.yavi.demo

import am.ik.yavi.builder.ValidatorBuilder
import am.ik.yavi.builder.validator
import am.ik.yavi.core.CustomConstraint

data class Book(val title: String, val isbn: String)

object ISBNValidator {
    private val ISBN_REGEX = Regex("^ISBN\\d{3}-\\d-\\d{6}-\\d{2}-\\d$")
    fun isISBN13(str: String?): Boolean {
        return str?.let { ISBN_REGEX.containsMatchIn(it) } ?: false
    }
}

object IsbnConstraint : CustomConstraint<String?> {
    override fun test(t: String?): Boolean {
        return ISBNValidator.isISBN13(t)
    }

    override fun messageKey(): String {
        return "string.isbn13"
    }

    override fun defaultMessageFormat(): String {
        return "\"{0}\" must be ISBN13 format"
    }
}

val bookValidator = ValidatorBuilder.of<Book>()
    .constraint(Book::title, "title") { it.notBlank().lessThanOrEqual(64) }
    .constraint(Book::isbn, "isbn") { it.notBlank().predicate(IsbnConstraint) }
    .build()

val bookValidatorKt = validator<Book> {
    Book::title {
        notBlank()
        lessThanOrEqual(64)
    }
    Book::isbn {
        notBlank()
        predicate(IsbnConstraint)
    }
}
