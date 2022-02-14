package com.thecodefather.whatsthepassword.internal

import android.content.Context
import android.util.Log
import com.thecodefather.whatsthepassword.internal.extensions.toTimestamp
import com.thecodefather.whatsthepassword.WhatsPasswordApplication as application
import org.joda.time.*
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 *
 * Created by Hussein Yassine on 05, March, 2019.
 *
 */

object DateUtilities {

    private const val MILLIS_IN_SECOND = 1000L
    private const val SECONDS_IN_MINUTE = 60L
    private const val MINUTES_IN_HOUR = 60L
    private const val HOURS_IN_DAY = 24L
    private const val DAYS_IN_YEAR = 365L
    const val MILLISECONDS_IN_YEAR  = MILLIS_IN_SECOND * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_YEAR

    fun todayEndOfDay(): Date {
        val calendar = getCalendar()
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        return calendar.time
    }
    fun today(): Date = getDateFromDateTime(Date(/*117, 11, 12*/))

    fun todayNoHours(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.time
    }

    fun dateTimeStartOfDay(): Date{
        return DateTime().withTimeAtStartOfDay().toDate()
    }

    fun getCurrentYear():String{
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.YEAR).toString()
    }

    fun getDatesDifferences(firstDate: Date, secondDate: Date = today()) =
        secondDate.time / 1000 - firstDate.time / 1000

    fun getWeekStart():Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        return calendar.time
    }

    fun getWeekEnd():Date {
        val calendar = getCalendarForDate(getWeekStart())
        calendar.add(Calendar.DAY_OF_WEEK, 6)
        return calendar.time
    }

    fun getCalendarForDate(date: Date = today()): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    fun getCalendar(date: Date = today(), startOfDay: Boolean = false, timezone: TimeZone? = null): Calendar {
        val calendar = if (timezone == null){
            Calendar.getInstance()
        }else{
            Calendar.getInstance(timezone)
        }
        calendar.time = date
        if (startOfDay){
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
        }
        return calendar
    }

    fun calendarByAdding(field: Int, amount: Int, calendar: Calendar = getCalendar(today())): Calendar {
        calendar.add(field, amount)
        return calendar
    }

    fun calendarBySetting(field: Int, amount: Int, calendar: Calendar = getCalendar(today())): Calendar {
        calendar.set(field, amount)
        return calendar
    }

    fun dateByAdding(
        field: Int,
        amount: Int,
        date: Date = today(),
        startOfDay: Boolean = false,
        timezone: TimeZone? = null
    ): Date {
        return calendarByAdding(field, amount, getCalendar(date, startOfDay, timezone)).time
    }

    fun dateByAddingDays(amount: Int, date: Date = today()): Date {
        return dateByAdding(Calendar.DATE, amount, date)
    }

    fun dateByAddingMonths(amount: Int, date: Date = today()): Date {
        return dateByAdding(Calendar.MONTH, amount, date)
    }

    fun dateByAddingYears(amount: Int, date: Date = today()): Date {
        return dateByAdding(Calendar.YEAR, amount, date)
    }

    fun getDateFromDateTime(date: Date): Date {
        return getCalendar(date).time
    }

    fun getCurrentDateFromTimeString(inputTime: String?) : Long {
        if (inputTime != null){
            val date = LocalDate.now().toLocalDateTime(LocalTime.parse(inputTime))
            return date.toDate().time
        }else{
            val date = LocalDate.now()
            return date.toDate().time
        }
    }

    fun getDaysBetweenDates(startDate: LocalDateTime, endDate: LocalDateTime):List<Date> {
        val dates: MutableList<Date> = mutableListOf()
        val calendar: Calendar = GregorianCalendar()
        calendar.time = startDate.toDate()

        while (calendar.time.before(endDate.toDate())) {
            val date = calendar.time
            dates.add(date)
            calendar.add(Calendar.DATE, 1)
        }
        return dates.toList()
    }

    fun getUTCDateFromNumberOfSeconds(numberOfSeconds: Long, date: Date = todayNoHours()): Long {
        return dateByAdding(
            field = Calendar.SECOND,
            amount = numberOfSeconds.toInt(),
            date = date,
            startOfDay = true,
            timezone = TimeZone.getTimeZone("UTC")
        ).time / 1000
    }

    fun getDateFromString(dateString: String?, pattern: String): Date {
        return if (dateString == null)
             today()
        else{

            val dtf = DateTimeFormat.forPattern(pattern)
            val dateTime = dtf.parseDateTime(dateString)
            dateTime.toDate()
        }
    }

    fun getFormattedDate(date: Date): String =
        SimpleDateFormat(
            Constants.DateFormats.FULL_DATE_DAY_FORMAT,
            Locale.ENGLISH
        ).format(date)


    fun getDateWithFormat(date: Date?, format: String): String {
        return if(date == null)
            ""
        else
            SimpleDateFormat(format, Locale.getDefault()).format(date)
    }

    fun checkInRange(date: Date, first: Date, last: Date): Boolean {
        return date in first..last
    }

    fun getTimeNowInMillis(): Long {
        val calendar = Calendar.getInstance()
        val now = calendar.timeInMillis
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        //return 20 * 60 * 60 * 1000
        val today = calendar.timeInMillis
        return now - today
    }

    fun getDateFromTimeInMillisOnly(millis: Long, matchToDate: Date?): Date {
        val startDate = Date(millis)
        val startDateCalendar = Calendar.getInstance()
        startDateCalendar.time = startDate
        startDateCalendar.timeZone = TimeZone.getTimeZone("UTC")
        if (matchToDate != null) {
            val cal = getCalendar(matchToDate)
            startDateCalendar.set(Calendar.YEAR, cal.get(Calendar.YEAR))
            startDateCalendar.set(Calendar.MONTH, cal.get(Calendar.MONTH))
            startDateCalendar.set(Calendar.DATE, cal.get(Calendar.DATE))
        }
        return startDateCalendar.time
    }

    fun getNowTimeUTC(): Long{
        return LocalDateTime.now(DateTimeZone.getDefault()).toTimestamp()
    }
}