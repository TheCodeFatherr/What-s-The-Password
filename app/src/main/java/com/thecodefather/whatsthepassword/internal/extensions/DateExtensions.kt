package com.thecodefather.whatsthepassword.internal.extensions

import com.thecodefather.whatsthepassword.internal.Constants
import com.thecodefather.whatsthepassword.internal.DateUtilities
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

/**
 *
 * Created by Hussein Yassine on 05, March, 2019.
 *
 */

val local = Locale("en_us_posix")

/**
 * To Custom Format
 */
fun Date.format(format: String): String {
    val sdf = SimpleDateFormat(format, local)
    return sdf.format(this)
}

fun String.toApiDate(): Date {
    return DateUtilities.getDateFromString(this, Constants.DateFormats.DATE_TIME_FORMAT_SECONDARY)
}

fun Long.formatToDate(): Date{
    return Date(this)
}
fun Long.formatToDefaultDate(): LocalDateTime {
    return DateTime(this * 1000, DateTimeZone.getDefault()).toLocalDateTime()
}

fun LocalDateTime.toTimestamp(): Long {
    return Timestamp(this.toDateTime().millis / 1000).time
}

fun Date.toTimestamp(): Long {
    val localDateTime = LocalDateTime.fromDateFields(this)
    return Timestamp(localDateTime.toDateTime().millis / 1000).time
}

fun LocalDateTime.formattedDate(format:String = Constants.DateFormats.FULL_DATE_DAY_FORMAT): String {
    return this.toDate().format(format)
}
/**
 * Pattern: yyyy-MM-dd
 */
fun Date.formatToServerDateDefaults(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd", local)
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy
 */
fun Date.formatToViewDateDefaults(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", local)
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToViewTimeDefaults(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", local)
    return sdf.format(this)
}

/**
 * Pattern: dd-MM-yyyy'T'HH:mm:ss Z
 */
fun Date.formatToRawDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    return sdf.format(this)
}


/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(field, amount)

    this.time = cal.time.time

    cal.clear()

    return this
}

fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}

fun Date.toTime(): String {
    val format = SimpleDateFormat("HH:mm", local)
    return format.format(this)
}