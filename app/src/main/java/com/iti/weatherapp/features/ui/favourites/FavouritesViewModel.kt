package com.iti.weatherapp.features.ui.favourites

import androidx.lifecycle.*
import com.iti.weatherapp.R
import com.iti.weatherapp.helper.MyApplication
import com.iti.weatherapp.helper.viewmodel.BaseViewModel
import com.iti.weatherapp.model.City
import com.iti.weatherapp.repo.CityRepository
import kotlinx.coroutines.launch

class FavouritesViewModel(private val repo: CityRepository) : BaseViewModel() {

    val items: LiveData<List<City>> = repo.allCities.asLiveData()

    fun addItem(city: City) = viewModelScope.launch{
        repo.insert(city)
    }

    fun removeItem(id: Int) = viewModelScope.launch{
        repo?.let { it.delete(id) }
    }

    override fun onCleared() {
        super.onCleared()
    }
}


class FavouritesViewModelFactory(private val repository: CityRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}