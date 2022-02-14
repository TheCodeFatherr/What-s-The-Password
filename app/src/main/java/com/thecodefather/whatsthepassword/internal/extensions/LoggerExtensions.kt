package com.thecodefather.whatsthepassword.internal.extensions
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics


fun Any.fastLog(message: String) {
    Log.wtf("[LOG]", message)
}

fun Any.warn(message: String) {
    Log.w(this::class.java.simpleName, message)
}

fun Any.debug(message: String) {
    Log.d("[DEBUG]", message)
}

fun Any.crashLog(title: String, e: Throwable){
    FirebaseCrashlytics.getInstance().recordException(e)
    Log.wtf(title, e.message)
}