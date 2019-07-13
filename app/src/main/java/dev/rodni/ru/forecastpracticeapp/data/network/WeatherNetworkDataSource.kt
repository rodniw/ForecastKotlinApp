package dev.rodni.ru.forecastpracticeapp.data.network

import androidx.lifecycle.LiveData
import dev.rodni.ru.forecastpracticeapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}