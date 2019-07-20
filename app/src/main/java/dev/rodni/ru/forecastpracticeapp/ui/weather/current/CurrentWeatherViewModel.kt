package dev.rodni.ru.forecastpracticeapp.ui.weather.current

import androidx.lifecycle.ViewModel;
import dev.rodni.ru.forecastpracticeapp.data.provider.UnitProvider
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository
import dev.rodni.ru.forecastpracticeapp.util.UnitSystem
import dev.rodni.ru.forecastpracticeapp.util.lazyDeffered

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository,
    private val unitProvider: UnitProvider
) : ViewModel() {

    private val unitSystem = unitProvider.getUnitSystem()

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeffered {
        forecastRepository.getCurrentWeather(isMetric)
    }

    val weatherLocation by lazyDeffered {
        forecastRepository.getWeatherLocation()
    }
}
