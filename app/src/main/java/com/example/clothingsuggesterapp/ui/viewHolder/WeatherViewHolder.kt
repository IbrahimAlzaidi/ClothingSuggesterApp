package com.example.clothingsuggesterapp.ui.viewHolder

import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clothingsuggesterapp.R
import com.example.clothingsuggesterapp.databinding.WeatherItemBinding
import com.example.clothingsuggesterapp.model.WeatherInfo
import com.example.clothingsuggesterapp.ui.adapters.OnWeatherItemClickListener
import com.example.clothingsuggesterapp.utils.WeatherUtils.getDayNameFromTimestamp
import kotlin.math.ceil

class WeatherViewHolder(
    private val binding: WeatherItemBinding,
    private val itemClickListener: OnWeatherItemClickListener,
    private val setSelectedPosition: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(weatherInfo: WeatherInfo, isSelected: Boolean) {
        binding.dayName.text = getDayNameFromTimestamp(weatherInfo.timestamp)
        binding.weatherDegree.text = "${ceil(weatherInfo.temperature.day).toInt()} C"
        loadWeatherIcon(weatherInfo.weather[0].icon, binding.iconWeather)

        updateBackgroundColor(isSelected)
        updateTextColor(isSelected)
        itemView.setOnClickListener {
            setSelectedPosition(adapterPosition)
            itemClickListener.onWeatherItemClick(weatherInfo)
        }
    }

    private fun updateBackgroundColor(isSelected: Boolean) {
        val backgroundColor = if (isSelected) R.color.white_select else android.R.color.transparent
        val background = ContextCompat.getDrawable(itemView.context, R.drawable.rounded_item_bg)?.mutate() as? GradientDrawable
        background?.setColor(ContextCompat.getColor(itemView.context, backgroundColor))
        itemView.background = background
    }

    private fun updateTextColor(isSelected: Boolean) {
        val textColor = if (isSelected) R.color.white else R.color.white
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