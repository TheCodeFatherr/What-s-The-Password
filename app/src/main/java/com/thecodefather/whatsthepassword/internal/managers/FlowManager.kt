package com.thecodefather.whatsthepassword.internal.managers

import com.thecodefather.whatsthepassword.WhatsPasswordApplication
import com.thecodefather.whatsthepassword.internal.PreferenceHelper
import com.thecodefather.whatsthepassword.internal.UserState


object FlowManager {

    private val preferences = PreferenceHelper.defaultPrefs(WhatsPasswordApplication.context)
    private const val IS_ONBOARDED = "is_onboarded"
    private const val IS_AUTHENTICATED = "is_authenticated"
    private const val IS_ADDED_PIN = "is_added_pin"


    fun isAddedPin(): Boolean {
        return preferences.getBoolean(IS_ADDED_PIN, false)
    }
    fun setAddedPin(value: Boolean) {
        preferences
                .edit()
                .putBoolean(IS_ADDED_PIN, value)
                .apply()
    }

    fun isUserOnBoarded(): Boolean {
        return preferences.getBoolean(IS_ONBOARDED, false)
    }
    fun setUserOnBoarded(value: Boolean) {
        preferences
                .edit()
                .putBoolean(IS_ONBOARDED, value)
                .apply()
    }

    fun isUserAuthenticated(): Boolean {
        return preferences.getBoolean(IS_AUTHENTICATED, false)
    }
    fun setAuthenticated(value: Boolean) {
        preferences
            .edit()
            .putBoolean(IS_AUTHENTICATED, value)
            .apply()
    }

    fun getUserState(): UserState {
        return when {
            isUserAuthenticated() -> {
                UserState.Authenticated
            }
            isAddedPin() -> {
                UserState.AddedPin
            }
            isUserOnBoarded() -> {
                UserState.Onboarded
            }
            else -> {
                UserState.Initial
            }
        }
    }
}