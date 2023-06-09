package com.example.clothingsuggesterapp.utils

import com.example.clothingsuggesterapp.R
import java.text.SimpleDateFormat
import java.util.*

object WeatherUtils {
    fun getDayNameFromTimestamp(timestamp: Long): String {
        val date = Date(timestamp * 1000L)
        return SimpleDateFormat("dd MMMM, EEEE", Locale.getDefault()).format(date)
    }

    fun getRandomImageForTemperature(
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
            listOf(R.drawable.normal1, R.drawable.normal2, R.drawable.normal3, R.drawable.normal4)
        val hotImages =
            listOf(R.drawable.light1, R.drawable.light2, R.drawable.light3, R.drawable.light4)

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