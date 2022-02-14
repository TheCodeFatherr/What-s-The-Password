package com.thecodefather.whatsthepassword.internal

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.util.TypedValue
import com.thecodefather.whatsthepassword.WhatsPasswordApplication as application


object CommonUtilities {

    fun convertToPx(context: Context, dimDp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dimDp.toFloat(), context.resources
                .displayMetrics
        ).toInt()
    }

    fun getOsVersion(): String {
        return String.format("%s %s", "Android", android.os.Build.VERSION.RELEASE)
    }

    fun getPlatform(): String {
        return "Android"
    }

    fun getManufacturer(): String {
        return android.os.Build.MANUFACTURER
    }

    fun getModel(): String {
        return android.os.Build.MANUFACTURER + " | " + android.os.Build.MODEL
    }

    fun getAppVersion(): String {
        return getVersion()
    }

    fun getVersion(): String {
        try {
            val packageInfo = application.context.packageManager
                .getPackageInfo(application.context.packageName, 0)
            return packageInfo.versionName //+ "." + packageInfo.versionCode.toString()
        } catch (e: PackageManager.NameNotFoundException) {
            Log.wtf("CommonUtilities", "fastLog calling getVersion", e)
        } catch (e: RuntimeException) {
            Log.wtf("CommonUtilities", "fastLog calling getVersion", e)
        }
        return ""
    }

    fun getBuildVersion(): String {
        return getBuildNumber().toString()
    }

    fun getBuildNumber(): Int {
        try {
            val packageInfo = application.context.packageManager.getPackageInfo(application.context.packageName, 0)
            return if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode.toInt()
            } else {
                packageInfo.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.wtf("CommonUtilities", "fastLog calling getBuildNumber", e)
        } catch (e: RuntimeException) {
            Log.wtf("CommonUtilities", "fastLog calling getBuildNumber", e)
        }
        return -1
    }

    fun getPackageName(): String {
        return try {
            val packageText = application.context.packageName
            packageText
        } catch (ex: Exception) {
            Log.wtf("getPackageName", "fastLog getting package name", ex)
            ""
        }
    }
}
