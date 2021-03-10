package com.iti.weatherapp.helper

import android.content.Context
import android.content.Intent

object Navigator {
    fun gotoScreen(c1: Context, c2: Class<*>?) {
        val n = Intent(c1, c2)
        c1.startActivity(n)
    }

    fun navigateToTripDetails(context: Context, tripId: String?) {
//        val n = Intent(context, TripDetailes::class.java)
//        n.putExtra("trip_id", tripId)
//        context.startActivity(n)
    }

}