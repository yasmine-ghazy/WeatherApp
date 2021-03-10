package com.iti.weatherapp.repo

import com.iti.weatherapp.helper.Constants
import com.iti.weatherapp.model.Weather
import com.iti.weatherapp.network.WeatherApiClient
import io.reactivex.Single

interface WeatherRepoInterface : BaseRepo{
    fun getItemsList(lat: String, lon: String, lang:String): Single<Weather>
}

object WeatherRepo : WeatherRepoInterface {

    //MARK: - API Calls Methods
    override fun getItemsList(lat: String, lon: String, lang:String): Single<Weather> {
        return WeatherApiClient.api.getWeather(lat,lon,lang , Constants.WEATHER_API_KEY)
    }

}