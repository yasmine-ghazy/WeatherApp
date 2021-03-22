package com.iti.weatherapp.network

import com.google.gson.GsonBuilder
import com.iti.weatherapp.helper.Constants
import com.iti.weatherapp.model.Cities
import io.reactivex.Observable

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface PlacesApiInterface {
    @GET("cities")
    fun getPlaces(
        @Query("limit") location: Int? = 10,
        @Query("namePrefix") searchText: String?,
        @Header("x-rapidapi-key") apiKey: String? = Constants.CITIES_API_KEY,
        @Header("x-rapidapi-host") host: String? = Constants.CITIES_API_HOST,
        @Header("useQueryString") useQuery: Boolean? = true

    ): Observable<Cities>
}

object PlacesApiClient{

    var api: PlacesApiInterface = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(Constants.CITIES_BASE_URL)
        .build().create(PlacesApiInterface::class.java)

}