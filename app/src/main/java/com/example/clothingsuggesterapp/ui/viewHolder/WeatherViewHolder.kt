package com.example.clothingsuggesterapp.ui.viewHolder

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothingsuggesterapp.R
import com.example.clothingsuggesterapp.databinding.WeatherItemBinding
import com.example.clothingsuggesterapp.model.WeatherInfo
import com.example.clothingsuggesterapp.ui.OnWeatherItemClickListener
import com.example.clothingsuggesterapp.utils.Utils.getDayNameFromTimestamp
import kotlin.math.ceil

class WeatherViewHolder(
    private val binding: WeatherItemBinding,
    private val itemClickListener: OnWeatherItemClickListener,
    private val setSelectedPosition: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(weatherInfo: WeatherInfo, isSelected: Boolean) {
        binding.dayName.text = getDayNameFromTimestamp(weatherInfo.timestamp)
        binding.weatherDegree.text = "${ceil(weatherInfo.temperature.day).toInt()}"
        loadWeatherIcon(weatherInfo.weather[0].icon, binding.iconWeather)

        updateBackgroundColor(isSelected)
        updateTextColor(isSelected)
        itemView.setOnClickListener {
            setSelectedPosition(adapterPosition)
            itemClickListener.onWeatherItemClick(weatherInfo)
        }
    }

    private fun updateBackgroundColor(isSelected: Boolean) {
        val backgroundColor = if (isSelected) R.color.blue else android.R.color.transparent
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, backgroundColor))
    }

    private fun updateTextColor(isSelected: Boolean) {
        val textColor = if (isSelected) R.color.white else R.color.black
        val resolvedColor = ContextCompat.getColor(itemView.context, textColor)
        binding.dayName.setTextColor(resolvedColor)
        binding.weatherDegree.setTextColor(resolvedColor)
    }

    private fun loadWeatherIcon(iconId: String, imageView: ImageView) {
        val iconUrl = "https://openweathermap.org/img/wn/$iconId.png"
        Glide.with(imageView.context)
            .load(iconUrl)
            .into(imageView)
    }
}