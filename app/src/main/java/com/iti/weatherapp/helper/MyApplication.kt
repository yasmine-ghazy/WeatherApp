package com.iti.weatherapp.helper

import android.app.Application
import android.content.Context
import com.iti.weatherapp.database.CityDatabase
import com.iti.weatherapp.repo.CityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MyApplication: Application() {

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { CityDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { CityRepository(database.cityDao()) }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = applicationContext
    }
    // contains function to return context
    companion object {
        private lateinit var INSTANCE: Context
        fun getContext() = INSTANCE
    }
}