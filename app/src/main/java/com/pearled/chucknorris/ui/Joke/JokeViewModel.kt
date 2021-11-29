package com.pearled.chucknorris.ui.Joke

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pearled.chucknorris.data.model.Joke
import com.pearled.chucknorris.data.network.RetrofitInstance
import com.pearled.chucknorris.utils.ChuckResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class JokeViewModel: ViewModel() {
    private val _joke: MutableLiveData<ChuckResult<Joke>> = MutableLiveData()
    val joke: LiveData<ChuckResult<Joke>> get() = _joke
    private val disposables = CompositeDisposable()
    private var category: String? = null

    fun updateCategory(newCategory: String) {
        if (newCategory != category) {
            category = newCategory
            fetchRandomJoke()
        }
    }

    fun fetchRandomJoke() {
        category?.let { category ->
            disposables.add(
                RetrofitInstance.buildService().getJoke(category)
                .delay(300, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    _joke.value = ChuckResult.Loading()
                }
                .subscribe({ it ->
                    _joke.value = ChuckResult.Success(it)
                }, {
                    _joke.value = ChuckResult.Failure(it.message ?: it.toString())
                    Log.e(null, it.message, it)
                })
            )
        }
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }}