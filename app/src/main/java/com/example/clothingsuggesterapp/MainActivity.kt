package com.example.clothingsuggesterapp


import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.clothingsuggesterapp.data.WeatherCallback
import com.example.clothingsuggesterapp.data.remote.Network
import com.example.clothingsuggesterapp.databinding.ActivityMainBinding
import com.example.clothingsuggesterapp.model.WeatherResponse
import com.example.clothingsuggesterapp.ui.adapters.WeatherAdapter
import com.example.clothingsuggesterapp.ui.base.BaseActivity
import com.example.clothingsuggesterapp.ui.viewHolders.getDayNameFromTimestamp
import com.example.clothingsuggesterapp.utils.PrefsUtil
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(), WeatherCallback {
    override val LOG_TAG: String? = MainActivity::class.simpleName
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun setup() {
        PrefsUtil.initPrefUtil(applicationContext)
        Network.makeRequestUsingOkhttp(this)

    }

    override fun addCallbacks() {}

    override fun onSuccess(weatherResponse: WeatherResponse) {

        val days = weatherResponse.list
        val currentTemperature = Math.ceil(weatherResponse.list[0].temperature.day).toInt()
        val lastClothName = PrefsUtil.clothName
        val (newImage, newClothName) = getRandomImageForTemperature(
            currentTemperature.toDouble(),
            lastClothName
        )

        runOnUiThread {
            binding?.dayTime?.text = getDayNameFromTimestamp(days[0].timestamp).toString()
            binding?.weatherDegre?.text = "${currentTemperature}°C"
            binding?.weatherIcon?.let {
                Glide.with(this@MainActivity)
                    .load("https://openweathermap.org/img/wn/${weatherResponse.list[0].weather[0].icon}.png")
                    .into(it)
            }
            binding?.clothePic?.setImageResource(newImage)
            PrefsUtil.clothName = newClothName
            binding?.recyclerView?.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            binding?.recyclerView?.adapter = WeatherAdapter(weatherResponse.list, this@MainActivity)

            binding?.suggestNext?.setOnClickListener {
                val (nextImage, nextClothName) = getRandomImageForTemperature(
                    currentTemperature.toDouble(),
                    PrefsUtil.clothName
                )
                binding?.clothePic?.setImageResource(nextImage)
                PrefsUtil.clothName = nextClothName
            }
        }
    }

    override fun onError(message: String) {
        Log.e(LOG_TAG, "Error: $message")
    }


    private fun getRandomImageForTemperature(
        temperature: Double,
        lastClothName: String?
    ): Pair<Int, String> {
        val coldImages = listOf(
            R.drawable.cold1,
            R.drawable.cold2,
            R.drawable.cold3,
            R.drawable.cold4,
            R.drawable.cold5,
            R.drawable.cold6
        )
        val normalImages =
            listOf(R.drawable.cold7, R.drawable.cold8, R.drawable.cold9, R.drawable.cold10)
        val hotImages = listOf(R.drawable.light1, R.drawable.light2, R.drawable.light3)

        val images = when {
            temperature <= 15 -> coldImages
            temperature in 16.0..24.0 -> normalImages
            else -> hotImages
        }

        val availableImages = if (lastClothName != null) {
            images.filter { it != lastClothName.toIntOrNull() }
        } else {
            images
        }

        val newImage = availableImages.random()
        return Pair(newImage, newImage.toString())
    }

}