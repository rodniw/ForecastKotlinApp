package dev.rodni.ru.forecastpracticeapp.ui.weather.current

import android.util.Log
import dev.rodni.ru.forecastpracticeapp.data.provider.UnitProvider
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository
import dev.rodni.ru.forecastpracticeapp.ui.base.WeatherViewModel
import dev.rodni.ru.forecastpracticeapp.util.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {

    private val TAG = "CurrentWeatherViewModel"

    init {
        Log.i(TAG, "init CurrentWeatherViewModel")
    }

    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather(isMetricUnit)
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared")
    }
}
