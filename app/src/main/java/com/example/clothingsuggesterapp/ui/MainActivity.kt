package com.example.clothingsuggesterapp.ui


import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.clothingsuggesterapp.databinding.ActivityMainBinding
import com.example.clothingsuggesterapp.model.WeatherInfo
import com.example.clothingsuggesterapp.model.WeatherListInfo
import com.example.clothingsuggesterapp.ui.adapters.OnWeatherItemClickListener
import com.example.clothingsuggesterapp.ui.adapters.WeatherAdapter
import com.example.clothingsuggesterapp.utils.PrefsUtil
import com.example.clothingsuggesterapp.utils.WeatherUtils.getDayNameFromTimestamp
import com.example.clothingsuggesterapp.utils.WeatherUtils.getRandomImageForTemperature
import kotlin.math.ceil

class MainActivity : AppCompatActivity(), IWeatherContract.View, OnWeatherItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private val presenter = WeatherPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter.attachView(this)
        presenter.initPrefsUtil(applicationContext)
        presenter.fetchWeather()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun displayWeather(weatherListInfo: WeatherListInfo) {
        runOnUiThread {
            setupViews(weatherListInfo)
            setupRecyclerView(weatherListInfo)
            setupSuggestNextButton()
        }
    }

    override fun showError(message: String) {
        Log.e(MainActivity::class.simpleName, "Error: $message")
    }

    private fun setupViews(weatherListInfo: WeatherListInfo) {
        val days = weatherListInfo.list
        val currentTemperature = ceil(days[0].temperature.day).toInt()

        binding.apply {
            dayTime.text = getDayNameFromTimestamp(days[0].timestamp)
            weatherDegre.text = "${currentTemperature}°C"
            weatherIcon.loadWeatherIcon(weatherListInfo.list[0].weather[0].icon)
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
        val (newImage, newClothName) = getRandomImageForTemperature(
            temperature.toDouble(),
            PrefsUtil.clothName
        )
        this.setImageResource(newImage)
        PrefsUtil.clothName = newClothName
    }

    private fun setupSuggestNextButton() {
        binding.suggestNext.setOnClickListener {
            binding.clothePic.updateClothePic(
                binding.weatherDegre.text?.toString()?.removeSuffix("°C")?.toInt() ?: 0
            )
        }
    }
    private fun setupRecyclerView(weatherListInfo: WeatherListInfo) {
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = WeatherAdapter(weatherListInfo.list, this@MainActivity)
        }
    }
    override fun onWeatherItemClick(weatherInfo: WeatherInfo) {
        val selectedTemperature = ceil(weatherInfo.temperature.day).toInt()
        binding.apply {
            dayTime.text = getDayNameFromTimestamp(weatherInfo.timestamp).toString()
            weatherDegre.text = "${selectedTemperature}°C"
            weatherIcon.loadWeatherIcon(weatherInfo.weather[0].icon)
            clothePic.updateClothePic(selectedTemperature)
        }
    }
}