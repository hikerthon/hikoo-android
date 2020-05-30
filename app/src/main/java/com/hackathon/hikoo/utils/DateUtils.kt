package com.hackathon.hikoo.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    val TIME_DATE_YEAR = "HH:mm MM/dd yyyy"
    val NORMAL = "yyyy/MM/dd"
    val DATE_WITH_TIME = "yyyy/MM/dd HH:mm"
    val BIRTHDAY = "dd MMMM yyyy"


    fun getLocale(): Locale {
        return Locale.getDefault()
    }

    fun getDate(timestamp: Long, patten: String) :String {
        val simpleDateFormat = SimpleDateFormat(patten, Locale.ENGLISH).also {
            it.timeZone = TimeZone.getTimeZone("Asia/Taipei")
        }

        return simpleDateFormat.format(Date(timestamp))
    }

    fun getFormattedDateAndTimeString(timestamp: Long): String {
        return getDate(timestamp, DATE_WITH_TIME)
    }

    fun getFormattedTimeAndDate(timestamp: Long): String {
        return getDate(timestamp,  TIME_DATE_YEAR)
    }

    fun getFormattedNormal(timestamp: Long): String {
        return getDate(timestamp, NORMAL)
    }

    fun getFormattedBirth(timestamp: Long): String {
        return getDate(timestamp, BIRTHDAY)
    }

    fun getRangeDate(startDate: Long, endDate: Long): String {
        val startDateString = getFormattedDateAndTimeString(startDate)
        val endDateString = getFormattedDateAndTimeString(endDate)
        return "$startDateString - $endDateString"
    }
}