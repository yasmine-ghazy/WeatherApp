package com.iti.weatherapp.helper

import java.text.SimpleDateFormat
import java.util.*

object UIHelper {
    fun getDateCurrentTimeZone(timestamp: Long, format: String): String? {
        try {
            val calendar: Calendar = Calendar.getInstance()
            val tz: TimeZone = TimeZone.getDefault()
            calendar.setTimeInMillis(timestamp * 1000)
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()))
            val sdf = SimpleDateFormat(format)
            val currentTimeZone: Date = calendar.getTime() as Date
            return sdf.format(currentTimeZone)
        } catch (e: Exception) {
        }
        return ""
    }


}