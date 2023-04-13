package com.example.clothingsuggesterapp.ui

import android.content.Context
import com.example.clothingsuggesterapp.R
import com.example.clothingsuggesterapp.data.Network
import com.example.clothingsuggesterapp.data.WeatherCallback
import com.example.clothingsuggesterapp.model.WeatherListInfo
import com.example.clothingsuggesterapp.utils.PrefsUtil
import java.text.SimpleDateFormat
import java.util.*

class WeatherPresenter : IWeatherContract.Presenter, WeatherCallback {
    private var view: IWeatherContract.View? = null
    private val network = Network

    override fun fetchWeather() {
        network.makeRequestUsingOkhttp(this)
    }

    override fun attachView(view: IWeatherContract.View) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onWeatherSuccess(weatherListInfo: WeatherListInfo) {
        view?.displayWeather(weatherListInfo)
    }

    override fun onWeatherError(message: String) {
        view?.showError(message)
    }

    fun initPrefsUtil(context: Context) {
        PrefsUtil.initPrefUtil(context)
    }

}