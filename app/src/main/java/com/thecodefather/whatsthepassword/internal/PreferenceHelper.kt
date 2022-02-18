package com.thecodefather.whatsthepassword.internal

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.thecodefather.whatsthepassword.internal.authentication.EncryptedMessage
import java.util.*

/**
 * Created by Hussein Yassine on 11/3/2017.
 */
object PreferenceHelper {

    private const val SHARED_PREFS_FILENAME = "biometric_prefs"
    fun defaultPrefs(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    /**
     * puts a key value pair in shared prefs if doesn't exists, otherwise updates value on given [key]
     */
    operator fun SharedPreferences.set(key: String, value: Any?): SharedPreferences {
        when (value) {
            is String? -> edit({ it.putString(key, value) })
            is Int -> edit({ it.putInt(key, value) })
            is Boolean -> edit({ it.putBoolean(key, value) })
            is Float -> edit({ it.putFloat(key, value) })
            is Long -> edit({ it.putLong(key, value) })
            is Date -> edit({ it.putLong(key, value.time) })
            is IntArray -> edit({ it.putString(key, value.joinToString(",")) })
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
        return this
    }

    /**
     * finds value on given key.
     * [T] is the type of value
     */
    inline operator fun <reified T : Any> SharedPreferences.get(key: String): T? =
            when (T::class) {
                String::class -> getString(key, null) as T?
                Int::class -> getInt(key, -1) as T?
                Boolean::class -> getBoolean(key, false) as T?
                Float::class -> getFloat(key, -1f) as T?
                Long::class -> getLong(key, -1) as T?
                Date::class -> if (contains(key)) (Date(getLong(key, -1)) as T) else null
                IntArray::class -> if (contains(key)) getString(key, null)?.split(",")?.map { it.toInt() }?.toIntArray() as T else null
                else -> throw UnsupportedOperationException("Not yet implemented")
            }

    /**
     * finds value on given key.
     * [T] is the type of value
     * @param defaultValue optional default value - will take null for strings, false for bool and -1 for numeric values if [defaultValue] is not specified
     */
    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T): T =
            get(key) ?: defaultValue

    /**
     * Saved and EncryptedMessage
     */
    fun storeEncryptedMessage(
        context: Context,
        prefKey: String,
        encryptedMessage: EncryptedMessage
    ) {
        val json = Gson().toJson(encryptedMessage)
        context.getSharedPreferences(SHARED_PREFS_FILENAME, Context.MODE_PRIVATE)
            .edit()
            .putString(prefKey, json).apply()
    }

    /**
     * Returns a single EncryptedMessage from prefKey
     */
    fun getEncryptedMessage(
        context: Context,
        prefKey: String
    ): EncryptedMessage? {
        val json = context.getSharedPreferences(SHARED_PREFS_FILENAME, Context.MODE_PRIVATE)
            .getString(prefKey, null)
        return Gson().fromJson(json, EncryptedMessage::class.java)
    }
}