package com.iti.weatherapp.network

import com.google.gson.GsonBuilder
import com.iti.weatherapp.helper.Constants
import com.iti.weatherapp.model.Weather
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET("onecall")
    fun getWeather(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("lang") lang: String?,
        @Query("units") units: String?,
        @Query("appid") appid: String?
    ): Single<Weather>
}

object WeatherApiClient{

    var api: WeatherApiInterface = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(Constants.WEATHER_BASE_URL)
        .build().create(WeatherApiInterface::class.java)

}