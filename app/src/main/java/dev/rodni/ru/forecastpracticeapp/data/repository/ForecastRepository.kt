package dev.rodni.ru.forecastpracticeapp.data.repository

import androidx.lifecycle.LiveData
import dev.rodni.ru.forecastpracticeapp.data.db.entity.WeatherLocation
import dev.rodni.ru.forecastpracticeapp.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getWeatherLocation() : LiveData<WeatherLocation>
}