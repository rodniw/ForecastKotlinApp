package dev.rodni.ru.forecastpracticeapp.data.repository

import androidx.lifecycle.LiveData
import dev.rodni.ru.forecastpracticeapp.data.db.entity.WeatherLocation
import dev.rodni.ru.forecastpracticeapp.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import dev.rodni.ru.forecastpracticeapp.data.db.unitlocalized.future.detail.UnitSpecificDetailFutureWeatherEntry
import dev.rodni.ru.forecastpracticeapp.data.db.unitlocalized.future.list.UnitSpecificSimpleFutureWeatherEntry
import org.threeten.bp.LocalDate

interface ForecastRepository {
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out UnitSpecificCurrentWeatherEntry>
    suspend fun getFutureWeatherList(startDate: LocalDate, metric: Boolean)
            : LiveData<out List<UnitSpecificSimpleFutureWeatherEntry>>
    suspend fun getFutureWeatherByDay(date: LocalDate, metric: Boolean)
            : LiveData<out UnitSpecificDetailFutureWeatherEntry>
    suspend fun getWeatherLocation() : LiveData<WeatherLocation>
}