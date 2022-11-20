package com.example.yavi.demo

import am.ik.yavi.message.MessageFormatter
import java.text.MessageFormat
import java.util.*

object ResourceBundleMessageFormatter : MessageFormatter {
    override fun format(
        messageKey: String,
        defaultMessageFormat: String,
        args: Array<out Any>,
        locale: Locale
    ): String {
        val resourceBundle = ResourceBundle.getBundle("messages", locale)
        val format = try {
            resourceBundle.getString(messageKey)
        } catch (e: MissingResourceException) {
            defaultMessageFormat
        }

        return MessageFormat(format, locale).format(args)
    }
}
