package com.example.clothingsuggesterapp.data

import com.example.clothingsuggesterapp.model.WeatherListInfo

interface WeatherCallback {
    fun onWeatherSuccess(weatherListInfo: WeatherListInfo)
    fun onWeatherError(message: String)
}