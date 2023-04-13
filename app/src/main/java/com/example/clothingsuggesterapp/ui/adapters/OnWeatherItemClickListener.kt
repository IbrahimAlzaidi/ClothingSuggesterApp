package com.example.clothingsuggesterapp.ui.adapters

import com.example.clothingsuggesterapp.model.WeatherInfo

interface OnWeatherItemClickListener {
    fun onWeatherItemClick(weatherInfo: WeatherInfo)
}