package com.example.clothingsuggesterapp.ui.viewHolders

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothingsuggesterapp.databinding.WeatherItemBinding
import com.example.clothingsuggesterapp.model.WeatherInfo
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewHolder(private val binding: WeatherItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(weatherInfo: WeatherInfo) {
        binding.dayName.text = getDayNameFromTimestamp(weatherInfo.timestamp)
        binding.weatherDegree.text = "${weatherInfo.temperature.day}°C"
        loadWeatherIcon(weatherInfo.weather[0].icon, binding.iconWeather)
    }

    private fun loadWeatherIcon(iconId: String, imageView: ImageView) {
        val iconUrl = "https://openweathermap.org/img/wn/$iconId.png"
        Glide.with(imageView.context)
            .load(iconUrl)
            .into(imageView)
    }
}

fun getDayNameFromTimestamp(timestamp: Long): String {
    val date = Date(timestamp * 1000L)
    return SimpleDateFormat("dd MMMM, EEEE", Locale.getDefault()).format(date)
}