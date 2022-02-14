package com.thecodefather.whatsthepassword.internal.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.thecodefather.whatsthepassword.WhatsPasswordApplication as application

object AnalyticsUtil {

    fun logEvent(
        eventName: String,
        params: Map<String, String>? = null
    ) {
        application.firebaseAnalytics.logEvent(eventName, params.toBundle())
    }

    fun setScreenViewInFirebase(itemName: String, screenParams: Map<String, String>? = null) {
        val params = mutableMapOf<String, String>()
        if (screenParams != null){
            params.putAll(screenParams)
        }
        params[FirebaseAnalytics.Param.SCREEN_NAME] = itemName
        params[FirebaseAnalytics.Param.SCREEN_CLASS] = "MainActivity"
        application.firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params.toBundle())
    }
}

fun Map<String, String>?.toBundle(): Bundle? {
    return if (this == null) {
        null
    } else {
        val bundle = Bundle()
        for ((key, value) in this.entries) {
            bundle.putString(key, value)
        }
        bundle
    }
}
