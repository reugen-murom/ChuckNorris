package com.pearled.chucknorris.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable

fun <T> Observable<T>.toLiveData() : LiveData<T> =
    MutableLiveData<T>().apply {
        this@toLiveData.subscribe { value = it }
    }