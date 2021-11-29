package com.pearled.chucknorris.data.network

import com.pearled.chucknorris.data.model.Joke
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ChuckNorrisApi {

    @GET("/jokes/categories")
    fun getCategories(): Observable<List<String>>

    @GET("/jokes/random")
    fun getJoke(@Query("category") category: String): Observable<Joke>

}
