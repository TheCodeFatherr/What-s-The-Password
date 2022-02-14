package com.thecodefather.whatsthepassword.internal


/**
 * Created by Hussein Yassine on 11/3/2017.
 */
object Constants {

    object Flavors{
        const val DEVELOPMENT = "development"
        const val PRODUCTION = "production"
    }

    object FileConstants {
        const val BASE_PATH = "WhatsThePassword"
        const val DATABASE_RESTORE_SUBDIRECTORY = "Database"
        const val DATABASE_NAME = "WhatsThePassword.db"
     }

    object DateFormats {
        const val DATE_FORMAT_PRIMARY = "yyyy-MM-dd"
        const val DATE_TIME_FORMAT_TERNARY = "yyyy-MM-dd'T'HH:mm:ss"
        const val DATE_TIME_FORMAT_SECONDARY = "yyyy-MM-dd'T'HH:mm:ssZ"
        const val DATE_TIME_FORMAT_PRIMARY = "yyyy-MM-dd'T'HH:mm:ssZ"
        const val TIME_FORMAT_PRIMARY = "HH:mm:ss"
        const val TIME_MIN_FORMAT_PRIMARY = "HH:mm"
        const val FULL_DATE_DAY_FORMAT = "EEE, MM, dd, yyyy"
    }
}
