package com.pearled.chucknorris.ui.List

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pearled.chucknorris.data.network.RetrofitInstance
import com.pearled.chucknorris.utils.ChuckResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ListViewModel: ViewModel() {

    private val _categoryList: MutableLiveData<ChuckResult<List<String>>> = MutableLiveData()
    val categoryList: LiveData<ChuckResult<List<String>>> get() = _categoryList
    private val disposables = CompositeDisposable()

    init {
        fetchCategories()
    }

    fun fetchCategories() {
        disposables.add(RetrofitInstance.buildService().getCategories()
            .delay(300, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                _categoryList.value = ChuckResult.Loading()
            }
            .subscribe({ it ->
                _categoryList.value = ChuckResult.Success(it)
            }, {
                _categoryList.value = ChuckResult.Failure(it.message ?: it.toString())
                Log.e(null, it.message, it)
            })
        )
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}