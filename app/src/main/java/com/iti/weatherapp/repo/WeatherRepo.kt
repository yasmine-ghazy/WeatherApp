package com.iti.weatherapp.repo

import android.util.Log
import com.iti.weatherapp.helper.Constants
import com.iti.weatherapp.helper.SharedKey
import com.iti.weatherapp.helper.SharedPreferenceHelper
import com.iti.weatherapp.model.Weather
import com.iti.weatherapp.network.WeatherApiClient
import io.reactivex.Single

interface WeatherRepoInterface : BaseRepo{
    fun getItemsList(lat: Double?, lon: Double?): Single<Weather>
}

object WeatherRepo : WeatherRepoInterface {

    val TAG = "WeatherRepo"

    //MARK: - API Calls Methods
    override fun getItemsList(lat: Double?, lon: Double?): Single<Weather> {
        val lang = if(SharedPreferenceHelper.readInt(SharedKey.LANGUAGE) == 0) "en" else "ar"
        val units = when(SharedPreferenceHelper.readInt(SharedKey.TEMP)){
            0 -> "metric"
            1 -> "imperial"
            else -> "standard"
        }

        Log.i(TAG, "getItemsList: $lat $lon")
        return WeatherApiClient.api.getWeather(lat,lon, lang, units,Constants.WEATHER_API_KEY)
    }

}