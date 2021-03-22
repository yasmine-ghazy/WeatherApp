package com.iti.weatherapp.features.ui.settings

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.weatherapp.R
import com.iti.weatherapp.helper.MyApplication
import com.iti.weatherapp.helper.SharedKey
import com.iti.weatherapp.helper.SharedPreferenceHelper

class SettingsViewModel() : ViewModel() {

    val TAG = "SettingsViewModel"

    private val settingsMap = mapOf(
            SharedKey.LANGUAGE to arrayOf(R.string.english, R.string.arabic),
            SharedKey.LOCATION to arrayOf(R.string.GPS, R.string.map),
            SharedKey.TEMP to arrayOf(R.string.celsius, R.string.kelvin,  R.string.fahrenheit),
            SharedKey.WIND to arrayOf(R.string.meter_sec, R.string.mile_hour),
            SharedKey.ALERT_TYPE to arrayOf(R.string.notification, R.string.alarm)
    )

    var settings: MediatorLiveData<MutableMap<SharedKey, Any>> = MediatorLiveData<MutableMap<SharedKey, Any>>()

    fun getSettings() {
        settings.value = mutableMapOf()
        settingsMap.forEach() {
            settings.value?.put(it.key, SharedPreferenceHelper.readInt(it.key))
        }
        settings.value?.put(SharedKey.ALERT_Time, SharedPreferenceHelper.readString(SharedKey.ALERT_Time))

        Log.i(TAG, "getSettings: "+ settings.value)
    }

    fun getTitle(key: SharedKey, context: Context): String{
        val id: Int = settingsMap[key]?.get(settings.value?.get(key) as Int)!!
        return context.resources.getString(id)
    }

    fun updateSettings(key: SharedKey, value: Any) {
        SharedPreferenceHelper.save(key, value)
        settings.value?.put(key, value)
        settings.postValue(settings.value)
        Log.i(TAG, "updateSettings: "+ settings.value)
    }

    fun getSelectedIndex(key: SharedKey): Int {
        return settings.value?.get(key) as Int
    }

    private fun getTitle(key: SharedKey, index: Int): String {
        return getString(settingsMap[key]?.get(index)!!)
    }

    private fun getString(id: Int): String = MyApplication.getContext().resources.getString(id)

}