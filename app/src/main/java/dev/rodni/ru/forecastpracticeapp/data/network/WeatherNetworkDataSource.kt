package dev.rodni.ru.forecastpracticeapp.data.network

import androidx.lifecycle.LiveData
import dev.rodni.ru.forecastpracticeapp.data.network.response.CurrentWeatherResponse
import dev.rodni.ru.forecastpracticeapp.data.network.response.FutureWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    val downloadedFutureWeather: LiveData<FutureWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )

    suspend fun fetchFutureWeather(
        location: String,
        languageCode: String
    )
}