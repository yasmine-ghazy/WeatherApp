package com.iti.weatherapp.repo

import androidx.annotation.WorkerThread
import com.iti.weatherapp.database.CityDao
import com.iti.weatherapp.model.City
import kotlinx.coroutines.flow.Flow

class CityRepository(private val dao: CityDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allCities: Flow<List<City>> = dao.getCities()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(city: City) {
        dao.insertCity(city)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: Int) {
        dao.deleteCityById(id)
    }
}