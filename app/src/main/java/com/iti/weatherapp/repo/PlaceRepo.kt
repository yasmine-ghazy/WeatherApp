package com.iti.weatherapp.repo

import com.iti.weatherapp.helper.Constants
import com.iti.weatherapp.model.model.Place
import com.iti.weatherapp.network.PlacesApiClient
import io.reactivex.Observable

interface PlaceRepoInterface : BaseRepo {
    fun getItemsList(text: String?): Observable<List<Place>>
}

object PlaceRepo : PlaceRepoInterface {
    //MARK: - API Calls Methods
    override fun getItemsList(text: String?): Observable<List<Place>>{
        return PlacesApiClient.api.getPlaces("30.06,31.25", text, Constants.PLACES_API_KEY)
            ?.map { result -> result.results }
            ?.flatMap { places -> Observable.fromIterable(places) }
            ?.filter { place ->
                place.resultType.equals("place") || place.resultType.equals("address")
            }
            ?.toList()
            ?.toObservable()!!
    }
}