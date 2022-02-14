package com.thecodefather.whatsthepassword.internal.managers

import com.thecodefather.whatsthepassword.internal.analytics.AnalyticsUtil


class AnalyticsManager {

    fun logScreenVisited(screenName: String?, params: MutableMap<String, String>? = null) {
        screenName?.let {
            AnalyticsUtil.setScreenViewInFirebase(screenName, params)
        }
    }
}
