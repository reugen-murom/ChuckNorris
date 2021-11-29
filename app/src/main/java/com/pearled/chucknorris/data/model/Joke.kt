package com.pearled.chucknorris.data.model

import com.google.gson.annotations.SerializedName

data class Joke(

    @SerializedName("icon_url")
    val iconUrl: String,

    @SerializedName("value")
    val text: String

)
