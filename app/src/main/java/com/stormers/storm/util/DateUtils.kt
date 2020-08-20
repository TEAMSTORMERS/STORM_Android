package com.stormers.storm.util

import java.lang.StringBuilder
import java.util.*

object DateUtils {

    fun getToday(): String {
        val calendar = GregorianCalendar()
        return StringBuilder()
            .append(calendar.get(Calendar.YEAR))
            .append("/")
            .append(calendar.get(Calendar.MONTH) + 1)
            .append("/")
            .append(calendar.get(Calendar.DAY_OF_MONTH))
            .toString()
    }
}