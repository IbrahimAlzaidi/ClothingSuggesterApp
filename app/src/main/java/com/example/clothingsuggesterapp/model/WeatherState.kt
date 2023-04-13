package com.example.clothingsuggesterapp.model

import com.google.gson.annotations.SerializedName

data class WeatherState(
    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String

)