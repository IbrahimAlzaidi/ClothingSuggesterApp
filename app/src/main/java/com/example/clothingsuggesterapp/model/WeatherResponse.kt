package com.example.clothingsuggesterapp.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("list")
    val list: List<WeatherInfo>
)