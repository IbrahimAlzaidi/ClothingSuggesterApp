package com.example.clothingsuggesterapp.data

import com.example.clothingsuggesterapp.model.WeatherListInfo
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

object Network {
    private val client = OkHttpClient()
    private const val baseUrl = "https://api.openweathermap.org/data/2.5/forecast/daily?q=berlin&appid=ed60fcfbd110ee65c7150605ea8aceea&units=metric"
    private val gson = Gson()

    fun makeRequestUsingOkhttp(callback: WeatherCallback) {
        val request = Request.Builder().url(baseUrl).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e.message ?: "Unknown error")
            }
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseString = response.body?.string()
                    val weatherListInfo = gson.fromJson(responseString, WeatherListInfo::class.java)
                    callback.onSuccess(weatherListInfo)
                } else {
                    callback.onError(response.message)
                }
            }
        })
    }
}