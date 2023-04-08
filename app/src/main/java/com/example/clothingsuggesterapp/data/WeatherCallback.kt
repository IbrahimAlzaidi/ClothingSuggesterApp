package com.example.clothingsuggesterapp.data

import com.example.clothingsuggesterapp.model.WeatherResponse

interface WeatherCallback {
    fun onSuccess(weatherResponse: WeatherResponse)
    fun onError(message: String)
}