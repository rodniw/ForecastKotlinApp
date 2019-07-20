package dev.rodni.ru.forecastpracticeapp.data.provider

import dev.rodni.ru.forecastpracticeapp.data.db.entity.WeatherLocation

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation: WeatherLocation) : Boolean
    //from device or from settings method
    suspend fun getPrefferedLocationString() : String
}