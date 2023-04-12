package com.example.clothingsuggesterapp.data

import com.example.clothingsuggesterapp.model.WeatherListInfo

interface WeatherCallback {
    fun onSuccess(weatherListInfo: WeatherListInfo)
    fun onError(message: String)
}