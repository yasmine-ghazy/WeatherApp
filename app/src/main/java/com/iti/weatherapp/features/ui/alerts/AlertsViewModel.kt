package com.iti.weatherapp.features.ui.alerts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.weatherapp.helper.viewmodel.BaseViewModel
import com.iti.weatherapp.repo.BaseRepo

class AlertsViewModel(val repo: BaseRepo) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is alerts Fragment"
    }
    val text: LiveData<String> = _text
}