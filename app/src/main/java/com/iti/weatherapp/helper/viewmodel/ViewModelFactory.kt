package com.iti.weatherapp.helper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.weatherapp.features.ui.favourites.FavouritesViewModel
import com.iti.weatherapp.features.ui.home.HomeViewModel
import com.iti.weatherapp.features.ui.settings.SettingsViewModel
import com.iti.weatherapp.repo.WeatherRepo

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(WeatherRepo) as T
            modelClass.isAssignableFrom(FavouritesViewModel::class.java) -> FavouritesViewModel(WeatherRepo) as T
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> SettingsViewModel() as T
            else -> throw ClassNotFoundException("No View Model Available")
        }
    }
}