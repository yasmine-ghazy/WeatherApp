package com.iti.weatherapp.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.*

object Language {
    fun setLanguage(context: Context, language: String?) {
        // Save selected language

        // Update language
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics())
    }

    fun currentLanguageString(context: Context): String? {
        return getString(context, currentLanguage(context))
    }

    fun getString(context: Context, identifier: String?): String? {
        val resourceId = context.resources.getIdentifier(identifier, "string", context.packageName)
        return context.resources.getString(resourceId)
    }

    fun currentLanguage(context: Context): String? {
        val languages = arrayOf("en" , "ar")
        val index = SharedPreferenceHelper.readInt(SharedKey.LANGUAGE)
        setLanguage(context, languages[index])
        return languages[index]
    }
}