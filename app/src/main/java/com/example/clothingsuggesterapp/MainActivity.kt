package com.example.clothingsuggesterapp


import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.clothingsuggesterapp.data.WeatherCallback
import com.example.clothingsuggesterapp.data.remote.Network
import com.example.clothingsuggesterapp.databinding.ActivityMainBinding
import com.example.clothingsuggesterapp.model.WeatherInfo
import com.example.clothingsuggesterapp.model.WeatherResponse
import com.example.clothingsuggesterapp.ui.OnWeatherItemClickListener
import com.example.clothingsuggesterapp.ui.adapters.WeatherAdapter
import com.example.clothingsuggesterapp.ui.base.BaseActivity
import com.example.clothingsuggesterapp.utils.PrefsUtil
import com.example.clothingsuggesterapp.utils.Utils.getDayNameFromTimestamp
import com.example.clothingsuggesterapp.utils.Utils.getRandomImageForTemperature
import kotlin.math.ceil

class MainActivity : BaseActivity<ActivityMainBinding>(), WeatherCallback,
    OnWeatherItemClickListener {
    override val LOG_TAG: String? = MainActivity::class.simpleName
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun setup() {
        PrefsUtil.initPrefUtil(applicationContext)
        Network.makeRequestUsingOkhttp(this)
    }

    override fun onSuccess(weatherResponse: WeatherResponse) {
        runOnUiThread {
            setupViews(weatherResponse)
            setupRecyclerView(weatherResponse)
            setupSuggestNextButton()
        }
    }

    private fun setupViews(weatherResponse: WeatherResponse) {
        val days = weatherResponse.list
        val currentTemperature = ceil(days[0].temperature.day).toInt()

        binding?.apply {
            dayTime.text = getDayNameFromTimestamp(days[0].timestamp).toString()
            weatherDegre.text = "${currentTemperature}°C"
            weatherIcon.loadWeatherIcon(weatherResponse.list[0].weather[0].icon)
            clothePic.updateClothePic(currentTemperature)
        }
    }

    private fun ImageView.loadWeatherIcon(icon: String) {
        val iconUrl = "https://openweathermap.org/img/wn/$icon.png"
        Glide.with(this.context)
            .load(iconUrl)
            .into(this)
    }

    private fun ImageView.updateClothePic(temperature: Int) {
        val (newImage, newClothName) = getRandomImageForTemperature(temperature.toDouble(), PrefsUtil.clothName)
        this.setImageResource(newImage)
        PrefsUtil.clothName = newClothName
    }

    private fun setupRecyclerView(weatherResponse: WeatherResponse) {
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = WeatherAdapter(weatherResponse.list, this@MainActivity)
        }
    }

    private fun setupSuggestNextButton() {
        binding?.suggestNext?.setOnClickListener {
            binding?.clothePic?.updateClothePic(binding?.weatherDegre?.text?.toString()?.removeSuffix("°C")?.toInt() ?: 0)
        }
    }

    override fun onError(message: String) {
        Log.e(LOG_TAG, "Error: $message")
    }

    override fun onWeatherItemClick(weatherInfo: WeatherInfo) {
        val selectedTemperature = ceil(weatherInfo.temperature.day).toInt()
        binding?.apply {
            weatherDegre.text = "${selectedTemperature}°C"
            weatherIcon.loadWeatherIcon(weatherInfo.weather[0].icon)
            clothePic.updateClothePic(selectedTemperature)
        }
    }
}