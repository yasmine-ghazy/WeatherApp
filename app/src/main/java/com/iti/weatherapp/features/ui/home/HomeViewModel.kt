package com.iti.weatherapp.features.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.weatherapp.helper.viewmodel.BaseViewModel
import com.iti.weatherapp.model.Weather
import com.iti.weatherapp.repo.BaseRepo
import com.iti.weatherapp.repo.WeatherRepo
import com.iti.weatherapp.repo.WeatherRepoInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(val repo: WeatherRepo? = null) : BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    var item: MediatorLiveData<Weather> = MediatorLiveData<Weather>()

    var compositeDisposable = CompositeDisposable()

    fun getItems(lat: String, lon: String) {
        repo?.let {
            compositeDisposable.add(it.getItemsList(lat, lon, "ar")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { weather: Weather?, error: Throwable? ->
                    item.apply {
                        value = weather
                    }

                    error.let {
                        print(error)
                    }
                })
        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}