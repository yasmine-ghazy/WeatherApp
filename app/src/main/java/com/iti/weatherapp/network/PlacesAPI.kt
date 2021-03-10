package com.iti.weatherapp.network

import com.google.gson.GsonBuilder
import com.iti.weatherapp.helper.Constants
import com.iti.weatherapp.model.model.PlaceResult
import io.reactivex.Observable

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface PlacesApiInterface {
    @GET("autosuggest")
    fun getPlaces(
        @Query("at") location: String?, @Query("q") searchText: String?,
        @Query("apiKey") apiKey: String?
    ): Observable<PlaceResult?>?
}

object PlacesApiClient{

    var api: PlacesApiInterface

    //MARK: - Methods
    init {
        api = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.PLACES_BASE_URL)
            .build().create(PlacesApiInterface::class.java)
    }
}