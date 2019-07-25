package dev.rodni.ru.forecastpracticeapp.ui.base

import androidx.lifecycle.ViewModel
import dev.rodni.ru.forecastpracticeapp.data.provider.UnitProvider
import dev.rodni.ru.forecastpracticeapp.data.repository.ForecastRepository
import dev.rodni.ru.forecastpracticeapp.util.UnitSystem
import dev.rodni.ru.forecastpracticeapp.util.lazyDeffered

abstract class WeatherViewModel(
    forecastRepository: ForecastRepository,
    unitProvider: UnitProvider
) : ViewModel() {
    private val unitSystem = unitProvider.getUnitSystem()

    val isMetricUnit: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weatherLocation by lazyDeffered {
        forecastRepository.getWeatherLocation()
    }
}