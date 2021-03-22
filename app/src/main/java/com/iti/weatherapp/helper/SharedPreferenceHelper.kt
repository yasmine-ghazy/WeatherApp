package com.iti.weatherapp.helper

import android.content.Context
import android.content.SharedPreferences


object SharedPreferenceHelper {
    private val sharedPref: SharedPreferences = MyApplication.getContext().getSharedPreferences("weather",
            Context.MODE_PRIVATE)
    // use a default value using new Date()
    fun save(key: SharedKey, value: Any):Boolean{
        return when {
            value is Int -> sharedPref.edit().putInt(key.toString(), value).commit()
            value is String -> sharedPref.edit().putString(key.toString(), value).commit()
            value is Boolean -> sharedPref.edit().putBoolean(key.toString(), value).commit()
            value is Float -> sharedPref.edit().putFloat(key.toString(), value).commit()
            value is Long -> sharedPref.edit().putLong(key.toString(), value).commit()
            else -> false
        }
    }

     fun readInt(key: SharedKey, default: Int = 0): Int = sharedPref.getInt(key.toString(),default)
     fun readString(key: SharedKey, default: String = ""): String = sharedPref.getString(key.toString() , default) ?: default
}


enum class SharedKey{
    LANGUAGE,
    LOCATION,
    TEMP,
    WIND,
    ALERT_TYPE,
    ALERT_Time
}