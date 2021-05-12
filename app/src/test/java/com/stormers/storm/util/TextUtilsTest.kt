package com.stormers.storm.util

import org.junit.Assert.assertEquals
import org.junit.Test

class TextUtilsTest {

    @Test
    fun isEmailPattern() {
        assertEquals(TextUtils.isEmailPattern("link5658@gmail.com"), true)
        assertEquals(TextUtils.isEmailPattern("link5658@gmail.comm"), false)
        assertEquals(TextUtils.isEmailPattern(" link5658@gmail.com"), false)
        assertEquals(TextUtils.isEmailPattern("link5658@gmail.com "), false)
        assertEquals(TextUtils.isEmailPattern("link5658@gmailcom"), false)
        assertEquals(TextUtils.isEmailPattern("link5658gmail.com"), false)
        assertEquals(TextUtils.isEmailPattern("12345@aaaa.com"), true)
        assertEquals(TextUtils.isEmailPattern("aaaa@1111.com"), true)
        assertEquals(TextUtils.isEmailPattern("a-b@c-b.com"), true)
    }
}