package com.iti.weatherapp.features.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.weatherapp.helper.viewmodel.BaseViewModel
import com.iti.weatherapp.repo.BaseRepo

class FavouritesViewModel(val repo: BaseRepo) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is favourites Fragment"
    }
    val text: LiveData<String> = _text
}