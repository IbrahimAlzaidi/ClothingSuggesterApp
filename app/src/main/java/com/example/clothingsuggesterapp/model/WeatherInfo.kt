package com.example.clothingsuggesterapp.model

import com.google.gson.annotations.SerializedName

data class WeatherInfo(
    @SerializedName("dt")
    val timestamp: Long,

    @SerializedName("weather")
    val weather: List<WeatherCondition>,

    @SerializedName("temp")
    val temperature: Temperature
)
