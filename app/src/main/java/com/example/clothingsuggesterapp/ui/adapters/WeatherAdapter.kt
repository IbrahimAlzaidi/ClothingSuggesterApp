package com.example.clothingsuggesterapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clothingsuggesterapp.databinding.WeatherItemBinding
import com.example.clothingsuggesterapp.model.WeatherInfo
import com.example.clothingsuggesterapp.ui.viewHolder.WeatherViewHolder

class WeatherAdapter(
    private val weatherList: List<WeatherInfo>,
    private val itemClickListener: OnWeatherItemClickListener
) : RecyclerView.Adapter<WeatherViewHolder>() {

    private var selectedPosition = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding, itemClickListener, ::setSelectedPosition)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position], position == selectedPosition)
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    private fun setSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }
}