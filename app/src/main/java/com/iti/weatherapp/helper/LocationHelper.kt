package com.iti.weatherapp.helper

import android.app.Activity
import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

object LocationHelper {
    //MARK: - Properties
    var PERMISSION_ID = 44
    var locationProvider: FusedLocationProviderClient? = null
    var location: Location? = null

    //init Properties
    fun setup(activity: Activity){
        locationProvider = LocationServices.getFusedLocationProviderClient(activity)
    }
}