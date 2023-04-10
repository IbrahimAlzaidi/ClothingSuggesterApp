package com.example.clothingsuggesterapp.ui

import com.example.clothingsuggesterapp.model.WeatherInfo

interface OnWeatherItemClickListener {
    fun onWeatherItemClick(weatherInfo: WeatherInfo)
}