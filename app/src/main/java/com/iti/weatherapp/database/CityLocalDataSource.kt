/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iti.weatherapp.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.iti.weatherapp.model.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Concrete implementation of a data source as a db.
 */
class CityLocalDataSource internal constructor(
    private val citiesDao: CityDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
){

    fun observeTasks(): LiveData<Result<List<City>>> {
        return citiesDao.observeCities().map {
            Result.Success(it)
        }
    }

     suspend fun refreshTask(taskId: String) {
        //NO-OP
    }

     suspend fun refreshTasks() {
        //NO-OP
    }

//     suspend fun getItems(): Result<List<City>> = withContext(ioDispatcher) {
//         return@withContext try {
//             Result.Success(citiesDao.getCities())
//         } catch (e: Exception) {
//             Result.Error(e)
//         }
//     }

     suspend fun save(city: City) = withContext(ioDispatcher) {
        citiesDao.insertCity(city)
    }

     suspend fun delete(id: Int) = withContext<Unit>(ioDispatcher) {
        citiesDao.deleteCityById(id)
    }
}
