package com.example.clothingsuggesterapp.ui

import com.example.clothingsuggesterapp.model.WeatherListInfo

interface IWeatherContract {
    interface View {
        fun displayWeather(weatherListInfo: WeatherListInfo)
        fun showError(message: String)
    }

    interface Presenter {
        fun fetchWeather()
        fun attachView(view: View)
        fun detachView()
    }
}