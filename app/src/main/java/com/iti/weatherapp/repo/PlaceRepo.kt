package com.iti.weatherapp.repo

import com.iti.weatherapp.model.City
import com.iti.weatherapp.network.PlacesApiClient
import io.reactivex.Observable

interface PlaceRepoInterface : BaseRepo {
    fun getItemsList(text: String?): Observable<List<City>>
}

object PlaceRepo : PlaceRepoInterface {
    //MARK: - API Calls Methods
    override fun getItemsList(text: String?): Observable<List<City>>{
        return PlacesApiClient.api.getPlaces( searchText = text)
            ?.map { result -> result.data }
            ?.flatMap { places -> Observable.fromIterable(places) }
            ?.toList()
            ?.toObservable()!!
    }


}