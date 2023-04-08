package com.example.clothingsuggesterapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clothingsuggesterapp.databinding.WeatherItemBinding
import com.example.clothingsuggesterapp.model.WeatherInfo
import com.example.clothingsuggesterapp.ui.viewHolders.WeatherViewHolder

class WeatherAdapter(private val weatherList: List<WeatherInfo>, private val context: Context) :
    RecyclerView.Adapter<WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = WeatherItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }
}