package com.stormers.storm.util

object TextUtils {

    private const val EMAIL_REGEX = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}\$"

    fun isEmailPattern(text: String): Boolean {
        return text.matches(Regex(EMAIL_REGEX))
    }
}