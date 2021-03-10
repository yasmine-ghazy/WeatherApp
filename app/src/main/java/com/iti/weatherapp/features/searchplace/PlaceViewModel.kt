package com.iti.weatherapp.features.searchplace

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.iti.weatherapp.model.model.Place
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
    var itemsList: MediatorLiveData<List<Place>> = MediatorLiveData<List<Place>>()
    var selectedItem: MediatorLiveData<Place?> = MediatorLiveData<Place?>()
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
            .switchMap(object : Function<String?, Observable<List<Place>>> {
                override fun apply(query: String): Observable<List<Place>>? {
                    return repo.getItemsList(query)
                        .subscribeOn(Schedulers.io())
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Place>> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onNext(places: List<Place>) {
                    itemsList.setValue(places)
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