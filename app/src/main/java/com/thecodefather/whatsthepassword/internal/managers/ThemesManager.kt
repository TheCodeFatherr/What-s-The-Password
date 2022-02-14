package com.thecodefather.whatsthepassword.internal.managers

import com.thecodefather.whatsthepassword.R
import com.thecodefather.whatsthepassword.WhatsPasswordApplication
import com.thecodefather.whatsthepassword.internal.PreferenceHelper
import com.thecodefather.whatsthepassword.internal.ThemeType
import com.thecodefather.whatsthepassword.internal.extensions.debug
import com.thecodefather.whatsthepassword.internal.extensions.fastLog
import com.thecodefather.whatsthepassword.internal.extensions.getStringResource


object ThemesManager {

    private val SELECTED_THEME_KEY = getStringResource(R.string.theme_shared_preferences_key)
    private val preferences = PreferenceHelper.defaultPrefs(WhatsPasswordApplication.context)

    fun getSelectedTheme(): String {
        val selectedTheme = preferences
            .getString(SELECTED_THEME_KEY, getStringResource(R.string.theme_system_default)) ?: ""
        if (selectedTheme.isEmpty()){
            preferences.edit().putString(SELECTED_THEME_KEY, getStringResource(R.string.theme_system_default)).apply()
        }
        return preferences.getString(SELECTED_THEME_KEY, getStringResource(R.string.theme_system_default)) ?: ""
    }

    fun getTheme(): ThemeType {
        debug("Log getTheme = " + getSelectedTheme())
        return when (getSelectedTheme()) {
            ThemeType.Light.name -> ThemeType.Light
            ThemeType.Dark.name -> ThemeType.Dark
            else -> {
                ThemeType.SystemDefault
            }
        }
    }

}