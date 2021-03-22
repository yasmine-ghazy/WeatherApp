package com.iti.weatherapp.features.searchplace

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.iti.weatherapp.model.City
import com.iti.weatherapp.repo.PlaceRepo
import com.iti.weatherapp.repo.PlaceRepoInterface
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class PlaceViewModel : ViewModel() {
    var itemsList: MediatorLiveData<List<City>> = MediatorLiveData<List<City>>()
    var selectedItem: MediatorLiveData<City?> = MediatorLiveData<City?>()
    private val repo: PlaceRepoInterface
    var compositeDisposable = CompositeDisposable()
    val subject = PublishSubject.create<String>()

    fun onQueryTextSubmit(s: String?) {
        subject.onComplete()
    }

    fun onQueryTextChange(text: String) {
        subject.onNext(text)
    }

    fun addSubject() {
        subject.debounce(100, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap(object : Function<String?, Observable<List<City>>> {
                override fun apply(query: String): Observable<List<City>>? {
                    return repo.getItemsList(query)
                        .subscribeOn(Schedulers.io())
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<City>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(cities: List<City>) {
                    itemsList.setValue(cities)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

                override fun onComplete() {}
            })
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

    init {
        repo = PlaceRepo
        addSubject()
    }
}