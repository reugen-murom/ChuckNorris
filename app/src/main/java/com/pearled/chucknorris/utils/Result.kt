package com.pearled.chucknorris.utils

sealed class ChuckResult<out T> {
    data class Success<T>(val value: T): ChuckResult<T>()
    data class Failure<T>(val message: String): ChuckResult<T>()
    class Loading<T>: ChuckResult<T>()
}